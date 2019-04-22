package pers.qly.micro.services.mvc.reactive.webflux;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @Author: NoNo
 * @Description: 可以看到 WebFlux 的执行计算和返回结果是同一个线程，所以 WebFlux 也可以是同步的
 * @Date: Create in 10:04 2019/1/8
 */
@RestController
public class WebFluxController {

    // Mono,单数据集合,类似 Optional 或者 RxJava 中的 Single,只能是0或1
    // Flux,多数据集合,类似 Collection 或者 RxJava 中 Observable,在0-N之间
    // 这里是非阻塞，会回调执行，也就是当前不阻塞，找个时间点回调
    @RequestMapping("")
    public Mono<String> index() {
        //执行计算
        println("执行计算!");
        Mono<String> result = Mono.fromSupplier(() -> {

            println("返回结果!");
            return "Hello World!";
        });

        return result;
    }

    private static void println(String message) {
        System.out.printf("[线程: %s] %s\n",
                Thread.currentThread().getName(),
                message);
    }
}
