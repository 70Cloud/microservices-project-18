package pers.qly.micro.services.mvc.reactive;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 22:55 2019/1/7
 */
public class ThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        println("Hello World!");

        AtomicBoolean done = new AtomicBoolean(false);

        final boolean isDone;// 不能用这个，因为后边是子线程，会线程不安全

        // volatile 易变，线程安全，保证可见性
        // final 不变，线程安全(一直不变)
        // 两者不能一起用

        Thread thread = new Thread(() -> {
            // 线程任务
            println("Hello World! 2018!");
            // CAS
            done.set(true);// 不通用

        },"sub-thread");

//        thread.setName("sub-thread");// 线程名字

        thread.start();

        // 线程 join() 方法
        thread.join();// 等待线程销毁

        println("Hello World 2 !");

        // CountDownLatch
        // CyclicBarrier
        // 都是 AQS -> 状态位，默认队列大小 Integer
    }

    private static void println(String message) {
        System.out.printf("[线程: %s] %s\n",
                Thread.currentThread().getName(),
                message);
    }
}
