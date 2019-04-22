package pers.qly.microservicesproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
public class MicroServicesProjectApplication {

    public static void main(String[] args) {
        // 第一种写法
//		SpringApplication.run(MicroServicesProjectApplication.class, args);

//        // 第二种写法
//		new SpringApplicationBuilder(MicroServicesProjectApplication.class)// Fluent API
//			// 单元测试是 PORT = RANDOM
//			.properties("server.port=0") // 随机向 OS 取一个可用端口
//			.run(args);

        // 等价于此写法
        SpringApplication springApplication = new SpringApplication(MicroServicesProjectApplication.class);
        Map<String, Object> properties = new LinkedHashMap<>();
        properties.put("server.port", 0);
        springApplication.setDefaultProperties(properties);
        // 设置非 Web 应用
//		springApplication.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext context = springApplication.run(args);
        // 这样是没有异常的
        // 因为 @SpringBootApplication 等同于 @SpringBootConfiguration，也等同于 @Configuration,最后等同于 @Component
        System.out.println(context.getBean(MicroServicesProjectApplication.class));
        // 输出当前的 Spring Boot 应用的 ApplicationContext 上下文的类名
        System.out.println("当前的Spring 应用上下文的类：" + context.getClass().getName());
    }
}
