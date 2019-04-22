package pers.qly.micro.services.mvc.reactive;

/**
 * @Author: NoNo
 * @Description: UnCaughtExceptionHandler 就是捕获异常不会报错
 * @Date: Create in 11:27 2019/1/10
 */
public class UnCaughtExceptionHandlerDemo {

    public static void main(String[] args) {

        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> {
            System.out.println(e.getMessage());
        });

        throw new RuntimeException("故意的！");
    }
}
