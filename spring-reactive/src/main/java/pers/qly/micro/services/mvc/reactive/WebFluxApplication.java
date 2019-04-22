package pers.qly.micro.services.mvc.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 10:12 2019/1/8
 */
@SpringBootApplication
public class WebFluxApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(WebFluxApplication.class);
        springApplication.run(args);
    }
}
