package pers.qly.micro.services.spring.cloud.service.discovery.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 21:39 2019/1/8
 */
@SpringBootApplication
@EnableDiscoveryClient// 尽可能使用 @EnableDiscoveryClient
public class ZKDSClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZKDSClientApplication.class, args);
    }
}
