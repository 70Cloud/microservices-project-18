package pers.qly.micro.services.spring.cloud.client.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 13:59 2019/1/9
 */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Qualifier// @Qualifier 不仅仅可以加名称，还可以进行类型过滤
public @interface CustomizedLoadBalanced {
}
