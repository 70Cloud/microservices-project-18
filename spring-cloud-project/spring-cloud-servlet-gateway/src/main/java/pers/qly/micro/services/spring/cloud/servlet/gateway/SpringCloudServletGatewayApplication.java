package pers.qly.micro.services.spring.cloud.servlet.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import pers.qly.micro.services.spring.cloud.servlet.gateway.loadbalancer.ZookeeperLoadBalancer;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 22:44 2019/1/9
 */
@SpringBootApplication
@EnableDiscoveryClient
@ServletComponentScan(basePackages = "pers.qly.micro.services.spring.cloud.servlet.gateway.servlet")
// 开启 Servlet 注解扫描，默认情况 Spring Boot 不会加载 Servlet 注解所标注的组件，比如说 @WebServlet
@EnableScheduling
public class SpringCloudServletGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudServletGatewayApplication.class, args);
    }

    /**
     * 依赖注入 ZookeeperLoadBalancer
     * @param discoveryClient
     * @return
     */
    @Bean
    public ZookeeperLoadBalancer zookeeperLoadBalancer(DiscoveryClient discoveryClient) {
        return new ZookeeperLoadBalancer(discoveryClient);
    }
}
