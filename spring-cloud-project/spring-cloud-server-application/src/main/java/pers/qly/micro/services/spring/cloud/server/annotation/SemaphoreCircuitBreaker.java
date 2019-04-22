package pers.qly.micro.services.spring.cloud.server.annotation;

import java.lang.annotation.*;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 16:58 2019/1/9
 */
@Target(ElementType.METHOD)// 标注在方法
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SemaphoreCircuitBreaker {

    /**
     * 信号量
     *
     * @return 设置信号量
     */
    int value();
}
