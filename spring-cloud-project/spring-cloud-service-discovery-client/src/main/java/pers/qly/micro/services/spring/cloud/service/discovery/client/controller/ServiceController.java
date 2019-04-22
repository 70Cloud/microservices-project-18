package pers.qly.micro.services.spring.cloud.service.discovery.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 21:55 2019/1/8
 */
@RestController
public class ServiceController {

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 返回所有的服务名称
     *
     * @return
     */
    @GetMapping("/services")
    public List<String> getAllServices() {
        return discoveryClient.getServices();
    }

    @GetMapping("/service/instances/{serviceName}")
    public List<String> getAllServiceInstances(@PathVariable String serviceName) {
        return discoveryClient.getInstances(serviceName)
                .stream()
                .map(s ->
                        s.getServiceId() + " - " + s.getHost() + ":" + s.getPort())
                .collect(Collectors.toList());
    }
}
