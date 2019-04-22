package pers.qly.micro.services.spring.cloud.client.loadbalance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 10:38 2019/1/9
 */
public class LoadBalancedRequestInterceptor implements ClientHttpRequestInterceptor {

    // 增加 loadBalance 改造后
    // Map Key:ServiceName Value:Urls
    private volatile Map<String, Set<String>> targetUrlsCache = new HashMap<>();

    @Autowired
    private DiscoveryClient discoveryClient;

    @Scheduled(fixedRate = 10 * 1000)// 10秒更新一次缓存
    public void updateTargetUrlsCache() {
        // 获取当前应用的机器列表
        Map<String, Set<String>> newTargetUrlsCache = new HashMap<>();

        discoveryClient.getServices().forEach(serviceName -> {
            List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(serviceName);
            // http://${ip}:${port}
            Set<String> newTargetUrls = serviceInstanceList
                    .stream()
                    .map(s ->
                            s.isSecure() ?
                                    "https://" + s.getHost() + ":" + s.getPort() :
                                    "http://" + s.getHost() + ":" + s.getPort())
                    .collect(Collectors.toSet());
            newTargetUrlsCache.put(serviceName, newTargetUrls);
        });
        // swap
        this.targetUrlsCache = newTargetUrlsCache;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // URI: /${app-name}/uri
        // URI: "/"+serviceName + "/say?message = "
        URI requestURI = request.getURI();
        String path = requestURI.getPath();
        String[] parts = StringUtils.split(path.substring(1), "/");
        String serviceName = parts[0];// serviceName
        String uri = parts[1]; // "/say?message = "

        // 拿到 targetUrls 服务器列表快照，避免 this.targetUrls 本地变量多次调用，保证线程安全
        List<String> targetUrls = new LinkedList<>(targetUrlsCache.get(serviceName));

        int size = targetUrls.size();
        // size = 3,index = 0-2
        int index = new Random().nextInt(size);
        // 选择其中一台服务器
        String targetURL = targetUrls.get(index);
        // 最终服务器 URI
        String actualURL = targetURL + "/" + uri + "?" + requestURI.getQuery();

        // 执行请求
        // 这种方法实现太麻烦了，底层都是 Java 实现
//        List<HttpMessageConverter<?>> messageConverters =
//                Arrays.asList(// 指定 2 种类型转换，不然会走 Json，会报错
//                        new ByteArrayHttpMessageConverter(),
//                        new StringHttpMessageConverter()
//                );
//
//        RestTemplate restTemplate = new RestTemplate(messageConverters);
//        // 响应内容
//        ResponseEntity<InputStream> responseEntity = restTemplate.getForEntity(actualURL,InputStream.class);

        System.out.println("本次请求的URL：" + actualURL);

        // 执行请求
        // 换一种实现方式，用 Java
        URL url = new URL(actualURL);
        URLConnection urlConnection = url.openConnection();
        // 响应头
        HttpHeaders httpHeaders = new HttpHeaders();
        // 响应主体
        InputStream responseBody = urlConnection.getInputStream();
        return new SimpleClientHttpResponse(httpHeaders, responseBody);
    }

    /**
     * 自定义实现 ClientHttpResponse
     */
    private static class SimpleClientHttpResponse implements ClientHttpResponse {

        private HttpHeaders headers;
        private InputStream body;

        public SimpleClientHttpResponse(HttpHeaders headers, InputStream body) {
            this.headers = headers;
            this.body = body;
        }

        @Override
        public HttpStatus getStatusCode() throws IOException {
            return HttpStatus.OK;
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return 200;
        }

        @Override
        public String getStatusText() throws IOException {
            return "OK";
        }

        @Override
        public void close() {

        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
