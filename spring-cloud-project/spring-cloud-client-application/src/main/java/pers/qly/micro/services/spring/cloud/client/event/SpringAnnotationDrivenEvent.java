package pers.qly.micro.services.spring.cloud.client.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 16:07 2019/1/10
 */
public class SpringAnnotationDrivenEvent {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // SpringAnnotationDrivenEvent 注册为 Spring Bean
        context.register(SpringAnnotationDrivenEvent.class);
        // 启动上下文
        context.refresh();
        // 确保上下文启动完毕后，再发送事件
        context.publishEvent(new MyApplicationEvent("Hello World!"));
        // 关闭上下文
        context.close();

    }

    private static class MyApplicationEvent extends ApplicationEvent {

        /**
         * Create a new ApplicationEvent.
         *
         * @param source the object on which the event initially occurred (never {@code null})
         */
        public MyApplicationEvent(Object source) {
            super(source);
        }
    }

//    @EventListener
//    public void onMessage(MyApplicationEvent applicationEvent) {
//        System.err.println("监听到 MyApplicationEvent 事件源：" + applicationEvent.getSource());
//    }

    @EventListener
    public void onMessage(Object eventSource) {
        System.err.println("监听到 MyApplicationEvent 事件源 : " + eventSource);
    }
}
