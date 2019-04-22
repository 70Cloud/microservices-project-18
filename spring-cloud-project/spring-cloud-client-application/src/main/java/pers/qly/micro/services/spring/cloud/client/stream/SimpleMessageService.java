package pers.qly.micro.services.spring.cloud.client.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 14:14 2019/1/10
 */
public interface SimpleMessageService {

    // Channel 名称
    @Output("myChannel")
    MessageChannel myChannel();// destination = test2018

    // Channel 名称
    @Output("test007")
    MessageChannel testChannel();// destination = test2018
}
