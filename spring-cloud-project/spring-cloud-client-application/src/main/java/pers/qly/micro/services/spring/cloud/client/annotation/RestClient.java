package pers.qly.micro.services.spring.cloud.client.annotation;

import java.lang.annotation.*;

/**
 * @Author: NoNo
 * @Description: Rest Client 注解
 * @Date: Create in 19:43 2019/1/9
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestClient {

    /**
     * Rest 服务应用名称
     *
     * @return
     */
    String name();
}
