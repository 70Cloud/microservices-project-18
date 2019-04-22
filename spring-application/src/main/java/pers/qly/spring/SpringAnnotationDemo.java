package pers.qly.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 23:19 2019/1/6
 */
@Configuration
public class SpringAnnotationDemo {

    public static void main(String[] args) {
        // XML 配置文件驱动 ClassPathXmlApplicationContext
        // Annotation驱动
        // 找 BeanDefinition
        // = @Bean @Configuration
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        // 注册一个 Configuration Class = SpringAnnotationDemo
        context.register(SpringAnnotationDemo.class);
        // 启动上下文
        context.refresh();

        System.out.println(context.getBean(SpringAnnotationDemo.class));
    }
}
