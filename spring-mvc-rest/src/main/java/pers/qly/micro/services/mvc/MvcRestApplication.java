package pers.qly.micro.services.mvc;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 19:51 2019/1/7
 */
//@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "pers.qly.micro.services.mvc.controller")
public class MvcRestApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MvcRestApplication.class)
                .run(args);
    }
}
