package pers.qly.micro.services.mvc.reactive;

import java.util.stream.Stream;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 23:22 2019/1/7
 */
public class StreamDemo {

    public static void main(String[] args) {
        Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9) // 0-9                      // 生产 Supplier
                .filter(v -> v % 2 == 1)// 判断数值->获取奇数
                .map(v -> v - 1) // 奇数变偶数                                // 处理
//                .forEach(System.out::println);// 消费属性 Consumer         // 消费 Consumer

                .reduce(Integer::sum)// 聚合操作
                .ifPresent(System.out::println);// 输出 0+2+4+6+8
    }
}
