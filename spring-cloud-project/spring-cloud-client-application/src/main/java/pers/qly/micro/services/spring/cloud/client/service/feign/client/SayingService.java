package pers.qly.micro.services.spring.cloud.client.service.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 19:26 2019/1/9
 */
@FeignClient(name = "spring-cloud-server-application")
public interface SayingService {

    @GetMapping("/say")
    String say(@RequestParam("message") String message);
}
