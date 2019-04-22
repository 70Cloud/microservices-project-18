package pers.qly.micro.services.spring.cloud.client.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 16:15 2019/1/10
 */
@RestController
public class SpringEventController implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @GetMapping("/send/event")
    public String sendEvent(@RequestParam String message) {
        publisher.publishEvent(message);
        return "Sent";
    }

    @EventListener
    public void onMessage(PayloadApplicationEvent event) {
        System.out.println("接收到事件：" + event.getPayload());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
