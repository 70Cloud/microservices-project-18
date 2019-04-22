package pers.qly.spring;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 23:19 2019/1/6
 */
public class DefaultResourceLoaderDemo {

    public static void main(String[] args) throws IOException {

        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

        resourceLoader.addProtocolResolver((location, loader) -> {
            if (location.startsWith("my://"))
                return new ClassPathResource(location.substring("my://".length()));
            return null;
        });

        Resource resource = resourceLoader.getResource("my://application.properties");

        InputStream inputStream = resource.getInputStream();

        System.out.println(StreamUtils.copyToString(inputStream, Charset.forName("UTF-8")));

    }
}
