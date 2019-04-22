package pers.qly.micro.services.mvc.reactive;

import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.GenericApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 22:13 2019/1/7
 */
public class SpringEventDemo {

    public static void main(String[] args) {

        // 默认是同步非阻塞(线程是main)
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();

        ExecutorService executors = Executors.newSingleThreadExecutor();

        // 默认同步，切换成异步非阻塞(线程是pool-1-thread-1)
        multicaster.setTaskExecutor(executors);

        // 增加事件监听器
        multicaster.addApplicationListener(event -> {
            // 事件监听
            System.out.printf("[线程: %s] event: %s\n", Thread.currentThread().getName(), event);
        });

        // 广播事件
        multicaster.multicastEvent(new PayloadApplicationEvent("Hello World!", "Hello World!"));
        multicaster.multicastEvent(new PayloadApplicationEvent("Hello World!", "Hello World!"));
        multicaster.multicastEvent(new PayloadApplicationEvent("Hello World!", "Hello World!"));

        executors.shutdown();
    }
}
