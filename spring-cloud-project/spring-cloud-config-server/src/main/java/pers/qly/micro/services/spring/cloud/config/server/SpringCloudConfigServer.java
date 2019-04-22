package pers.qly.micro.services.spring.cloud.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: NoNo
 * @Description: 自定义实现 Spring Cloud Config 配置中心
 * @Date: Create in 19:28 2019/1/8
 */
@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigServer {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(SpringCloudConfigServer.class);

        springApplication.run(args);
    }

    /**
     * 自定义实现 Spring Cloud Config 配置中心
     *
     * @return
     */
    @Bean
    public EnvironmentRepository environmentRepository() {

        return (String application, String profile, String label) -> {

            Environment environment = new Environment("default", profile);

            List<PropertySource> propertySources = environment.getPropertySources();

            Map<String, Object> source = new HashMap<>();

            source.put("name", "NoNo");

            PropertySource propertySource = new PropertySource("map", source);
            // 追加 PropertySource
            propertySources.add(propertySource);

            return environment;
        };
    }
}
