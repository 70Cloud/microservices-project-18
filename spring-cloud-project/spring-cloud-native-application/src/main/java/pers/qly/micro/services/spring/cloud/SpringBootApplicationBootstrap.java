package pers.qly.micro.services.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 15:41 2019/1/8
 */
@EnableAutoConfiguration
@RestController
public class SpringBootApplicationBootstrap {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext();
        parentContext.setId("NoNo");
        // 在 "NoNo" 中注册一个 "helloWorld" String 类型的 Bean
        parentContext.registerBean("helloWorld", String.class, "Hello World!");
        // 启动"NoNo"上下文
        parentContext.refresh();

        // 类比与 Spring WebMVC , Root WebApplication 和 DispatcherServlet WebApplication
        //DispatcherServlet WebApplication 的 parent = Root WebApplication
        //DispatcherServlet是一个Servlet
        // Filter 也要用 Root WebApplication 中的 Bean

        new SpringApplicationBuilder(SpringBootApplicationBootstrap.class)
                .parent(parentContext)// 显式地设置双亲上下文
                .run(args);
    }

    @Autowired// String message Bean
    @Qualifier("helloWorld")// Bean 名称来自于 NoNo 上下文
    private String message;

    @RequestMapping("")
    public String index() {
        return message;
    }
}
