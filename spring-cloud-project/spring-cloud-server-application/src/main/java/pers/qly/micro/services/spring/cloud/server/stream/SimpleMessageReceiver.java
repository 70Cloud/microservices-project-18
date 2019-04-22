package pers.qly.micro.services.spring.cloud.server.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 14:33 2019/1/10
 */
public interface SimpleMessageReceiver {

    @Input("myChannel")
    SubscribableChannel myChannel();

    @Input("test007")
    SubscribableChannel testChannel();
}
