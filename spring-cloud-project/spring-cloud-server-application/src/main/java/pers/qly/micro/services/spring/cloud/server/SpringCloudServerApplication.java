package pers.qly.micro.services.spring.cloud.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Indexed;
import pers.qly.micro.services.spring.cloud.server.stream.SimpleMessageReceiver;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 22:57 2019/1/8
 */
@SpringBootApplication// 标准 Spring Boot 应用
@EnableDiscoveryClient// 激活服务发现客户端
@EnableHystrix// 激活 Hystrix
@EnableAspectJAutoProxy(proxyTargetClass = true)// 激活 AOP，类进行代理
@EnableBinding(SimpleMessageReceiver.class) // 激活并引入 SimpleMessageReceiver
@EnableAsync// 异步执行
//@Indexed// 加索引，编译后会生成一个 spring.components,其中会把所有标注 @Indexed 的类放进去
public class SpringCloudServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudServerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @Autowired
    private SimpleMessageReceiver simpleMessageReceiver;

    /**
     * 接口编程
     */
    @PostConstruct
    public void init() {
        // 获取 SubscribableChannel
        SubscribableChannel subscribableChannel = simpleMessageReceiver.myChannel();
        subscribableChannel.subscribe(message -> {
            MessageHeaders headers = message.getHeaders();
            String encoding = (String) headers.get("charset-encoding");
            String text = (String) headers.get("content-type");
            byte[] conent = (byte[]) message.getPayload();

            try {
                // 接收到消息：[B@73f41f3d 变成字节数组了，可以通过调整 encoding 来实现
                System.out.println("接收到消息：" + new String(conent, encoding));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Spring Cloud Stream 注解编程
     * 三种实现，注意不要对同一个 Channel 做订阅，还要对消息做幂等性处理
     * 相同的编程模型重复执行，不同的编程模型轮流执行
     *
     * @param data 这个有点问题，header找不到了
     */
    @StreamListener("myChannel")
    public void onMessage(byte[] data) {
        System.out.println("onMessage(byte[]):" + new String(data));
    }

    /**
     * Spring Cloud Stream 注解编程
     *
     * @param data 这个有点问题，header 找不到了
     */
    @StreamListener("myChannel")
    public void onMessage(String data) {
        System.out.println("onMessage(String):" + data);
    }

    /**
     * Spring Cloud Stream 注解编程
     *
     * @param data2 这个有点问题，header找不到了
     */
    @StreamListener("myChannel")
    public void onMessage2(String data2) {
        System.out.println("onMessage(String):" + data2);
    }

    /**
     * Spring Integration 注解驱动
     *
     * @param data
     */
    @ServiceActivator(inputChannel = "myChannel")
    public void onServiceActivator(String data) {
        System.out.println("onServiceActivator(String):" + data);
    }

    /**
     * 注解驱动
     *
     * @param data
     */
    @StreamListener("test007")
    public void onMessageFromRocketMQ(byte[] data) {
        System.out.println("RocketMQ - onMessage(String):" + new String(data));
    }
}
