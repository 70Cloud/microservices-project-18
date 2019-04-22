package pers.qly.micro.services.spring.cloud.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.*;
import pers.qly.micro.services.spring.cloud.client.event.RemoteAppEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: NoNo
 * @Description: 远程应用事件控制器
 * @Date: Create in 16:51 2019/1/10
 */
@RestController
public class RemoteAppEventSenderController implements ApplicationEventPublisherAware {

    @Value("${spring.application.name}")
    public String currentAppName;

    private ApplicationEventPublisher publisher;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/send/remote/event")
    public String sendEvent(@RequestParam String message) {
        publisher.publishEvent(message);
        return "Sent";
    }

//    /**
//     * 对集群发送
//     *
//     * @param appName
//     * @param message
//     * @return
//     */
//    @GetMapping("/send/remote/event/{appName}")
//    public String sendAppCluster(@PathVariable String appName, @RequestBody String message) {
//        // 发送数据到自己
//        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(appName);
//        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(currentAppName, message, appName, serviceInstances);
//        // 发送事件给当前的上下文
//        publisher.publishEvent(remoteAppEvent);
//        return "OK";
//    }

//    /**
//     * 对单实例发送
//     * @param appName
//     * @param ip
//     * @param port
//     * @param data
//     * @return
//     */
//    @PostMapping("/send/remote/event/{appName}/{ip}/{port}")
//    public String sendAppInstance(@PathVariable String appName,
//                                  @PathVariable String ip,
//                                  @PathVariable int port,
//                                  @RequestBody Object data) {
//        ServiceInstance serviceInstance = new DefaultServiceInstance(appName,ip,port,false);
//        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(currentAppName, data, appName, Arrays.asList(serviceInstance));
//        // 发送事件给当前的上下文
//        publisher.publishEvent(remoteAppEvent);
//        return "OK";
//    }

    /**
     * 对集群发送(改造版)
     *
     * @param appName
     * @param data
     * @return
     */
    @PostMapping("/send/remote/event/{appName}")
    public String sendAppCluster(@PathVariable String appName, @RequestBody Object data) {
        // 发送数据到自己
        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(data, appName, true);
        // 发送事件给当前的上下文
        publisher.publishEvent(remoteAppEvent);
        return "OK";
    }

    /**
     * 对单实例发送
     *
     * @param appName
     * @param ip
     * @param port
     * @param data
     * @return
     */
    @PostMapping("/send/remote/event/{appName}/{ip}/{port}")
    public String sendAppInstance(@PathVariable String appName,
                                  @PathVariable String ip,
                                  @PathVariable int port,
                                  @RequestBody Object data) {
        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(data, appName, false);
        // 发送事件给当前的上下文
        publisher.publishEvent(remoteAppEvent);
        return "OK";
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
