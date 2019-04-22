package pers.qly.micro.services.spring.cloud.client.service.rest.client;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pers.qly.micro.services.spring.cloud.client.annotation.RestClient;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.Stream;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 19:45 2019/1/9
 */
@RestClient(name = "${saying.rest.service.name}")
public interface SayingRestService {

    @GetMapping("/say")
    String say(@RequestParam String message);// 请求参数和方法参数同名

    // 接口也可以写 main 方法哦
    public static void main(String[] args) {
        Method method = ReflectionUtils.findMethod(SayingRestService.class, "say", String.class);

//        Parameter parameter = method.getParameters()[0];
//        System.out.println(parameter.getName()); // arg0
//        parameter.isNamePresent();

        ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();

        // FIXME.. 接口好像不能获取到参数
        Stream.of(discoverer.getParameterNames(method)).forEach(System.out::println);
    }
}
