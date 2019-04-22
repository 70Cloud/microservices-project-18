package pers.qly.micro.services.spring.cloud.client.annotation;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 20:12 2019/1/9
 */
public class RequestMappingMethodInvocationHandler implements InvocationHandler {

    // ParameterNameDiscoverer : Spring 中的一个类，可以找到参数名称，因为反射是拿不到参数名称的，只能拿到参数类型
    private final ParameterNameDiscoverer parameterNameDiscoverer =
            new DefaultParameterNameDiscoverer();

    private final String serviceName;

    private final BeanFactory beanFactory;

    public RequestMappingMethodInvocationHandler(String serviceName, BeanFactory beanFactory) {
        this.serviceName = serviceName;
        this.beanFactory = beanFactory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 过滤 @GetMapping 方法
        GetMapping getMapping = findAnnotation(method, GetMapping.class);
        if (getMapping != null) {
            // 得到URI
            String[] uri = getMapping.value();
            // http://${serviceName}/${uri}
            // FIXME.. 简单实现，只取一个 URI
            StringBuilder urlBuilder = new StringBuilder("http://").append(serviceName).append(uri[0]);

            // 获取方法参数数量
            int count = method.getParameterCount();
            // 获取方法参数名称，是有顺序的
            String[] paramNames = parameterNameDiscoverer.getParameterNames(method);
            // 方法参数类型集合
            Class<?>[] paramTypes = method.getParameterTypes();
            // 方法注解集合
            Annotation[][] annotations = method.getParameterAnnotations();

            StringBuilder queryStringBuilder = new StringBuilder();

            for (int i = 0; i < count; i++) {
                Annotation[] paramAnnotations = annotations[i];
                RequestParam requestParam = (RequestParam) paramAnnotations[0];
                if (requestParam != null) {
                    String paramName = paramNames[i];
                    Class<?> paramType = paramTypes[i];
                    // HTTP 请求参数
                    String requestParamName = StringUtils.hasText(requestParam.value()) ? requestParam.value() :
                            paramName;
                    String requestParamValue = String.class.equals(paramType) ?
                            (String) args[i] : String.valueOf(args[i]);
                    // uri?name=value&n2=v2&n3=v3
                    // 拼接 uri 后的参数
                    queryStringBuilder.append("&")
                            .append(requestParamName).append("=").append(requestParamValue);
                }
            }

            String queryString = queryStringBuilder.toString();
            // 如果有字符的话，拼接
            if (StringUtils.hasText(queryString)) {
                urlBuilder.append("?").append(queryString);
            }
            // http://${serviceName}/${uri}?${queryString}
            String url = urlBuilder.toString();

            // 获取 RestTemplate,Bean 名称为"loadBalancedRestTemplate"
            // 获得 BeanFactory
            RestTemplate restTemplate = beanFactory.getBean("loadBalancedRestTemplate", RestTemplate.class);

            return restTemplate.getForObject(url, method.getReturnType());
        }
        return null;

    }

}
