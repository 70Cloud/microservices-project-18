package pers.qly.micro.services.spring.cloud.server.web.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: NoNo
 * @Description: Web MVC 配置
 * @Date: Create in 15:35 2019/1/9
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new CircuitBreakerHandlerInterceptor());
    }
}
