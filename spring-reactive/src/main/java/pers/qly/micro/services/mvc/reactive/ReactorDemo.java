package pers.qly.micro.services.mvc.reactive;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.stream.Stream;

/**
 * @Author: NoNo
 * @Description: 函数式接口：接口中只有一个非default方法 Spring Cloud Binding 接口
 * @Date: Create in 23:33 2019/1/7
 */
public class ReactorDemo {

    public static void main(String[] args) throws InterruptedException {

//        println(Flux.just(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)// 直接执行
//                .filter(v -> v % 2 == 1)// 判断数值->获取奇数
//                .map(v -> v - 1)// 奇数变偶数
//                .reduce(Integer::sum)// 聚合操作
//                .subscribeOn(Schedulers.elastic())// 这边是异步了，需要加 block
//                .block());
////                .subscribe(ReactorDemo::println)); // 订阅才执行

        Flux.just(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)// 直接执行
                .filter(v -> v % 2 == 1)// 判断数值->获取奇数
                .map(v -> v - 1)// 奇数变偶数
                .reduce(Integer::sum)// 聚合操作
                .subscribeOn(Schedulers.elastic())// 这边是异步了，弹性，根据你的需要变化线程数，最大为 Integer 的最大值
//                .subscribeOn(Schedulers.parallel())
                .subscribe(ReactorDemo::println);// 订阅才执行

        Thread.sleep(1000);

    }

    private static void println(Object message) {
        System.out.printf("[线程: %s] %s\n",
                Thread.currentThread().getName(),
                message);
    }
}
