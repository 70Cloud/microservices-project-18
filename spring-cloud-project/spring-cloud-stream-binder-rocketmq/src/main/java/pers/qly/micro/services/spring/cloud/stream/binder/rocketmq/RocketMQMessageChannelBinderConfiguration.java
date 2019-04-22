package pers.qly.micro.services.spring.cloud.stream.binder.rocketmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 21:38 2019/1/10
 */
@Configuration
public class RocketMQMessageChannelBinderConfiguration {

    @Bean
    public RocketMQMessageChannelBinder rocketMQMessageChannelBinder() {
        return new RocketMQMessageChannelBinder();
    }

}
