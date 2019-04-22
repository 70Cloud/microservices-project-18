package pers.qly.micro.services.spring.cloud.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: NoNo
 * @Description: 远程事件接收 控制器
 * @Date: Create in 17:19 2019/1/10
 */
@RestController
public class RemoteAppEventReceiverController implements ApplicationEventPublisherAware {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ApplicationEventPublisher publisher;

    @PostMapping("/receive/remote/event/")
    public String receive(@RequestBody Map<String, Object> data) {// REST 请求不需要具体类型，传个Map过来就行
        // 事件的发送者
        String sender = (String) data.get("sender");
        // 事件的数据内容
        Object value = data.get("value");
        // 事件类型
        String type = (String) data.get("type");

        logger.info("接收到事件！");

        // 接收到对象内容，同样也要发送事件到本地做处理
        publisher.publishEvent(new SenderRemoteAppEvent(value, sender));
        return "received";
    }

    private static class SenderRemoteAppEvent extends ApplicationEvent {

        private final String sender;

        public SenderRemoteAppEvent(Object source, String sender) {
            super(source);
            this.sender = sender;
        }

        public String getSender() {
            return sender;
        }
    }

    @EventListener
    @Async// 加了就变成异步执行了，不然是同步执行
    public void onEvent(SenderRemoteAppEvent event) {
        logger.info("接收到事件源：" + event.getClass().getSimpleName() + ",来自于应用: " + event.getSender());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
