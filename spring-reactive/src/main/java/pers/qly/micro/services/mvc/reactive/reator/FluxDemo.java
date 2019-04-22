package pers.qly.micro.services.mvc.reactive.reator;

import reactor.core.publisher.Flux;

import java.util.stream.Stream;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 14:33 2019/1/14
 */
public class FluxDemo {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
//        Flux.just(1, 2, 3).subscribe(System.out::println); // 慢

        Stream.of(1, 2, 3).forEach(System.out::println); // 快
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
