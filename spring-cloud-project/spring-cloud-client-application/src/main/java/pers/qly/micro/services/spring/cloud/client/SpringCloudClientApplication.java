package pers.qly.micro.services.spring.cloud.client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableScheduling;
import pers.qly.micro.services.spring.cloud.client.annotation.EnableRestClient;
import pers.qly.micro.services.spring.cloud.client.event.HttpRemoteAppEventListener;
import pers.qly.micro.services.spring.cloud.client.service.feign.client.SayingService;
import pers.qly.micro.services.spring.cloud.client.service.rest.client.SayingRestService;
import pers.qly.micro.services.spring.cloud.client.stream.SimpleMessageService;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 22:57 2019/1/8
 */
@SpringBootApplication// 标准 Spring Boot 应用
@EnableDiscoveryClient// 激活服务发现客户端
@EnableScheduling// 激活异步调度
@EnableFeignClients(clients = SayingService.class)// 引入 FeignClient
@EnableRestClient(clients = SayingRestService.class)// 引入 RestClient
@EnableBinding(SimpleMessageService.class) // 激活并引入 SimpleMessageService
public class SpringCloudClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .listeners(new HttpRemoteAppEventListener())// 启动时加入自定义监听事件
                .run(args);
    }
}
