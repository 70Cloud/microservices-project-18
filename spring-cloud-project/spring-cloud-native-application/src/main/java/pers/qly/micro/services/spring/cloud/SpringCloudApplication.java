package pers.qly.micro.services.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 15:19 2019/1/8
 */
@SpringBootApplication
public class SpringCloudApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringCloudApplication.class);

        springApplication.run(args);
    }
}
