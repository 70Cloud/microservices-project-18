package pers.qly.micro.services.mvc.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 21:15 2019/1/7
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service // 它是 @Service 组件
@Transactional // 它是事务注解
public @interface TransactionService {// @Service + @Transaction

    @AliasFor(annotation = Service.class)
    String value();// 服务名称

    @AliasFor(annotation = Transactional.class, attribute = "value")
    String txName();
}
