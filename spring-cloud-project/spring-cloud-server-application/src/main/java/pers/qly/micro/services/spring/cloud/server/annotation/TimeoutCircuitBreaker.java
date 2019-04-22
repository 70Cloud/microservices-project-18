package pers.qly.micro.services.spring.cloud.server.annotation;

import java.lang.annotation.*;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 16:43 2019/1/9
 */
@Target(ElementType.METHOD)// 标注在方法
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TimeoutCircuitBreaker {

    /**
     * 超时时间
     *
     * @return 设置超时时间
     */
    long timeout();
}
