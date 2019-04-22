package pers.qly.micro.services.spring.cloud.client.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 15:57 2019/1/10
 */
public class SpringEvent {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(Object.class);

        context.addApplicationListener(event -> {
            System.err.println("监听：" + event.getClass().getSimpleName());
        });

        context.refresh();
        context.start();
        context.stop();
        context.close();
    }
}
