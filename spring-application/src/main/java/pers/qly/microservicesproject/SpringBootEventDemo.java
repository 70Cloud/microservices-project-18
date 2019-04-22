package pers.qly.microservicesproject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 11:45 2019/1/7
 */
//@SpringBootApplication
@EnableAutoConfiguration
public class SpringBootEventDemo {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringBootEventDemo.class)
                .listeners(event -> { // 增加监听器
                    System.err.println("监听到事件 ： " + event.getClass().getSimpleName());
                })
                .run(args)// 运行
                .close();
    }
}
