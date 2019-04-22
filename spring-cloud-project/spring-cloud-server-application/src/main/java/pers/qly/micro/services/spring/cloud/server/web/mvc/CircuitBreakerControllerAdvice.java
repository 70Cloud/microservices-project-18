package pers.qly.micro.services.spring.cloud.server.web.mvc;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.qly.micro.services.spring.cloud.server.controller.ServerController;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeoutException;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 15:42 2019/1/9
 */
@RestControllerAdvice(assignableTypes = ServerController.class)
public class CircuitBreakerControllerAdvice {

    @ExceptionHandler
    public void onTimeoutException(TimeoutException ex,// 这边有个瑕疵，会把所有的 TimeoutException 都拦截，解决方法，指定拦截某个类
                                   Writer writer) throws IOException {
        writer.write(errorContent(""));// 网络I/O是被容器管理的
        writer.flush();
//        writer.close();// 这边关不关都没问题，底层都会 close()
    }

    public String errorContent(String message) {
        return "Fault";
    }
}
