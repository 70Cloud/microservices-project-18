package pers.qly.micro.services.spring.cloud.client.service.rest.client;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pers.qly.micro.services.spring.cloud.client.annotation.RestClient;

import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * @Author: NoNo
 * @Description: 测试获取方法参数名称
 * @Date: Create in 19:45 2019/1/9
 */
public class SayingRestServiceImpl {

    public String say(String message){// 请求参数和方法参数同名
        return "Hello";
    }

    // 接口也可以写 main 方法哦
    public static void main(String[] args) {

        Method method = ReflectionUtils.findMethod(SayingRestServiceImpl.class, "say", String.class);

        ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();

        Stream.of(discoverer.getParameterNames(method)).forEach(System.out::println);
    }
}
