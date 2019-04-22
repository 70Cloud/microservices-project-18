package pers.qly.micro.services.mvc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.DefaultTransactionStatus;
import pers.qly.micro.services.mvc.service.EchoService;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 21:21 2019/1/7
 */
@ComponentScan(basePackages = "pers.qly.micro.services.mvc.service")
@EnableTransactionManagement
public class SpringApplication {

    @Component("myTxName")
    public static class MyPlatFormTransactionManager implements PlatformTransactionManager {

        @Override
        public TransactionStatus getTransaction(@Nullable TransactionDefinition definition) throws TransactionException {
            return new DefaultTransactionStatus(
                    null, true, true, definition.isReadOnly(), true, null);
        }

        @Override
        public void commit(TransactionStatus status) throws TransactionException {
            System.out.println("commit()...");
        }

        @Override
        public void rollback(TransactionStatus status) throws TransactionException {
            System.out.println("rollback()...");
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册 SpringApplication 扫描 pers.qly.micro.services.mvc.service
        context.register(SpringApplication.class);

        context.refresh();// 启动

        context.getBeansOfType(EchoService.class).forEach((beanName, bean) -> {
            System.err.println("Bean Name:" + beanName + ",Bean:" + bean);

            bean.echo("Hello World!");
        });

        context.close(); // 关闭
    }
}
