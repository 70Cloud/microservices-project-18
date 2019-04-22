package pers.qly.micro.services.spring.cloud.server.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.qly.micro.services.spring.cloud.server.annotation.SemaphoreCircuitBreaker;
import pers.qly.micro.services.spring.cloud.server.annotation.TimeoutCircuitBreaker;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author: NoNo
 * @Description: 服务熔断实现，基本都是基于{@link Future}
 * @Date: Create in 22:59 2019/1/8
 */
@RestController
public class ServerController {

    private final static Random random = new Random();

    @Value("${spring.application.name}")
    private String currentServiceName;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * 简易版本，无容错实现及带容错版本
     *
     * @param message
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/simple/say")
    public String simpleSay(@RequestParam String message) throws Exception {

        Future<String> future = executorService.submit(() -> {
            return doSimpleSay(message);
        });

        String returnValue = null;
        // 带容错版本
        try {
            // 100ms 超时时间
            returnValue = future.get(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {// Java 7的语法，多 catch
            // 超级容错 = 执行错误或者超时
            returnValue = errorContent(message);
        }

        return returnValue;
    }

    private String doSimpleSay(String message) throws Exception {
        // 如果随机时间 大于等于 100ms ，那么触发容错
        int value = random.nextInt(200);
        System.out.println("simpleSay() costs " + value + " ms.");
        // > 100
        Thread.sleep(value);
        String returnValue = "Simple Say: " + message;
        System.out.println(returnValue);
        return returnValue;
    }

    /**
     * 中级版本
     * 缺点：代码具有侵入性，可以采用 AOP 优化
     *
     * @param message
     * @return
     * @throws Exception
     */
    @GetMapping("/middle/say")
    public String middleSay(@RequestParam String message) throws Exception {

        Future<String> future = executorService.submit(() -> {
            return doSimpleSay(message);
        });

        String returnValue = null;
        try {
            // 100ms 超时时间
            returnValue = future.get(100, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);// 解决容错后还能收到返回结果的问题
            throw e;
        }

        return returnValue;
    }

    /**
     * 高级版本，采用 AOP 优化，无注解实现
     *
     * @param message
     * @return
     * @throws Exception
     */
    @GetMapping("/advanced/say")
    public String advancedSay(@RequestParam String message) throws Exception {

        return doSimpleSay(message);
    }

    /**
     * 高级版本 + 注解(超时时间)
     *
     * @param message
     * @return
     * @throws Exception
     */
    @GetMapping("/advanced/say2")
    @TimeoutCircuitBreaker(timeout = 100)
    public String advancedSay2(@RequestParam String message) throws Exception {

        return doSimpleSay(message);
    }

    /**
     * 高级版本 + 注解(信号量)
     *
     * @param message
     * @return
     * @throws Exception
     */
    @GetMapping("/advanced/say3")
    @SemaphoreCircuitBreaker(5)
    public String advancedSay3(@RequestParam String message) throws Exception {

        return doSimpleSay(message);
    }

    @HystrixCommand(
            fallbackMethod = "errorContent",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "100")
            }
    )
    @GetMapping("/say")
    public String say(@RequestParam String message) throws InterruptedException {
        // 如果随机时间 大于等于 100ms ，那么触发容错
        int value = random.nextInt(200);

        System.out.println("say() costs " + value + " ms.");

        // > 100
        Thread.sleep(value);

        System.out.println("ServerController 接收到消息 - say : " + message);
        return "Hello " + message;
    }

    /**
     * 方法参数需要一样
     * @param message
     * @return
     */
    public String errorContent(String message) {
        return "Fault";
    }
}
