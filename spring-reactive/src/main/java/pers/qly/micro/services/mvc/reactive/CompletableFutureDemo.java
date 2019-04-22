package pers.qly.micro.services.mvc.reactive;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: NoNo
 * @Description: CompletableFuture
 * 第二步是在第一步基础上做事的，存在依赖关系
 * 主要作用
 * 1、提供异步操作
 * 2、提供 Future 链式操作
 * 3、提供函数式编程
 * @Date: Create in 22:36 2019/1/7
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {

        // 这样是在同一个线程中的，但是实现是异步
//        println("当前线程");
//        CompletableFuture.supplyAsync(() -> {
//            println("第一步返回\"Hello\"");
//            return "Hello";// 第一步返回"Hello"
//        }).thenApply(result -> {
//            println("第二步在第一步结果+\" World!\"");
//            return result + " World!";// 第二步在第一步结果+" World!"
//        }).thenAccept(CompletableFutureDemo::println)
//                .join()// 等待执行结束
//        ;// 控制输出

        // Reactive programming
        // 编程风格:
        //  Fluent 流畅的
        //  Streams 流式的
        // 业务效果:
        //  大多数业务逻辑是数据操作,流程编排
        // 函数式语言特性(Java 8+)
        //  消费类型 Consumer
        //  转换类型 Function
        //  提升/减少维度 map/reduce/flatMap

        // 这样还是同一个线程
        // 这段代码是非阻塞执行的
        // thenApplyAsync 这边为什么没有跨线程呢
        // 因为 main() 和 supplyAsync() 已经是异步操作了，下边再进行异步操作没价值了，没必要切换，这边做了优化
        println("当前线程");
        CompletableFuture.supplyAsync(() -> {
            println("第一步返回\"Hello\"");
            return "Hello";// 第一步返回"Hello"
        }).thenApplyAsync(result -> {
            println("第二步在第一步结果+\" World!\"");
            return result + " World!";// 第二步在第一步结果+" World!"
        }).thenAccept(CompletableFutureDemo::println)// 控制输出
                .whenComplete((returnValue, error) -> {// 返回值 void，异常->结束状态
                    println("执行结束！");
                })
                .join()// 等待执行结束
        ;

        // 三段式编程
        // 业务执行->执行完成->异常处理
        // 三段式的问题，不是流式(好处：一目了然，流程编排)
        // 命令编程方式 Imperative programming
//        try{
//           //action
//        }catch (Exception e){
//            //error
//        }finally {
//            //complete
//        }
    }

    private static void println(String message) {
        System.out.printf("[线程: %s] %s\n",
                Thread.currentThread().getName(),
                message);
    }
}
