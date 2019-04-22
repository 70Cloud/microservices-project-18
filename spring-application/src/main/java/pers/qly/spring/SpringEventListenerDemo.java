package pers.qly.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.GenericApplicationContext;

public class SpringEventListenerDemo {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();

        // 添加事件监听器
//        context.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
//            @Override
//            public void onApplicationEvent(ApplicationEvent event) {
////                System.err.println("监听事件 : " + event.getSource());
//                System.err.println("监听事件 : " + event);
//            }
//        });

        // 添加自定义监听器
        context.addApplicationListener(new ClosedEventListener());
        context.addApplicationListener(new ContextRefreshedListener());

        // 启动 Spring 应用上下文
        context.refresh();

        // 一个是 ContextRefreshedEvent
        // 一个是 PayloadApplicationEvent
        // Spring 应用上下文发布事件
        context.publishEvent("Hello World!");// 发布一个"Hello World!"内容的事件
        // 一个是 MyEvent
        context.publishEvent(new MyEvent("Hello World!!"));

        // 关闭应用上下文
        // 一个是 ContextClosedEvent
        context.close();
    }

    /**
     * 通过 ApplicationListener 的泛型对 ContextClosedEvent 进行监听
     */
    private static class ClosedEventListener implements ApplicationListener<ContextClosedEvent> {

        @Override
        public void onApplicationEvent(ContextClosedEvent event) {
            System.err.println("关闭上下文：" + event);
        }
    }

    /**
     * 通过 ApplicationListener 的泛型对 ContextRefreshedEvent 进行监听
     */
    private static class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            System.err.println("启动上下文：" + event);
        }
    }

    /**
     * 自定义事件
     */
    private static class MyEvent extends ApplicationEvent {

        public MyEvent(Object source) {
            super(source);
        }
    }
}
