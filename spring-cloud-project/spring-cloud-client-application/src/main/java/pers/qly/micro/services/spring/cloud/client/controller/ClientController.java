package pers.qly.micro.services.spring.cloud.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pers.qly.micro.services.spring.cloud.client.annotation.CustomizedLoadBalanced;
import pers.qly.micro.services.spring.cloud.client.loadbalance.LoadBalancedRequestInterceptor;
import pers.qly.micro.services.spring.cloud.client.service.feign.client.SayingService;
import pers.qly.micro.services.spring.cloud.client.service.rest.client.SayingRestService;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 22:59 2019/1/8
 */
@RestController
public class ClientController {

    @Autowired// 依赖注入 自定义 RestTemplate Bean
    @CustomizedLoadBalanced
    private RestTemplate restTemplate;

    @Autowired// 依赖注入 Ribbon RestTemplate Bean
    @LoadBalanced // 注意注解的派生性，此处 @LoadBalanced 就是 @Qualifier,利用 @LoadBalanced 来进行过滤
    private RestTemplate loadBalancedRestTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String currentServiceName;

    @Autowired
    private SayingService sayingService;

    @Autowired
    private SayingRestService sayingRestService;

    // 增加 server-application 改造前
    // 线程安全
//    private Set<String> seviceNames = new ConcurrentSkipListSet<>();
//    private volatile Set<String> targetUrls = new HashSet<>();
//
//    /**
//     * 更新目标 URL 缓存
//     */
//    @Scheduled(fixedRate = 10 * 1000)// 10 秒更新一次缓存
//    public void updateTargetUrlsCache() {
//        // 获取当前应用的机器列表
//        Set<String> oldTargetUrls = this.targetUrls;
//        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(currentServiceName);
//        // http://${ip}:${port}
//        Set<String> newTargetUrls = serviceInstanceList
//                .stream()
//                .map(s ->
//                        s.isSecure() ?
//                                "https://" + s.getHost() + " : " + s.getPort() :
//                                "http://" + s.getHost() + " : " + s.getPort())
//                .collect(Collectors.toSet());
//        // swap
//        this.targetUrls = newTargetUrls;
//
//        oldTargetUrls.clear();// 方便GC
//    }
//
//    @GetMapping("/invoke/say")// -> /say
//    public String invokeSay(@RequestParam String message) {
//        // 拿到 targetUrls 服务器列表快照，避免 this.targetUrls 本地变量多次调用，保证线程安全
//        List<String> tragetUrls = new ArrayList<>(this.targetUrls);// 用本地变量把Url保存过来
////        this.targetUrls;
////        this.targetUrls;// 这样的 2 次调用有线程安全问题
//
//        int size = tragetUrls.size();
//        // size = 3,index = 0-2
//        int index = new Random().nextInt(size);
//        // 选择其中一台服务器
//        String targetUrl = tragetUrls.get(index);
//        // RestTemplate 发送请求到服务器
//        // 输出响应
//        return restTemplate.getForObject(targetUrl + "/say?message = " + message, String.class);
//    }

    // 增加 server-application 改造后
    // Map Key:ServiceName Value:Urls
    private volatile Map<String, Set<String>> targetUrlsCache = new HashMap<>();

    @Scheduled(fixedRate = 10 * 1000)// 10 秒更新一次缓存
    public void updateTargetUrlsCache() {
        // 获取当前应用的机器列表
        Map<String, Set<String>> oldTargetUrlsCache = this.targetUrlsCache;
        Map<String, Set<String>> newTargetUrlsCache = new HashMap<>();

        discoveryClient.getServices().forEach(serviceName -> {
            List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(serviceName);
            // http://${ip}:${port}
            Set<String> newTargetUrls = serviceInstanceList
                    .stream()
                    .map(s ->
                            s.isSecure() ?
                                    "https://" + s.getHost() + " : " + s.getPort() :
                                    "http://" + s.getHost() + " : " + s.getPort())
                    .collect(Collectors.toSet());
            newTargetUrlsCache.put(serviceName, newTargetUrls);
        });

        // swap
        this.targetUrlsCache = newTargetUrlsCache;

//        oldTargetUrlsCache.clear();// 方便GC
    }

    @GetMapping("/invoke/{serviceName}/say")// -> /say
    public String invokeSay(@PathVariable String serviceName,
                            @RequestParam String message) {
        // 自定义 RestTemplate发送请求到服务器
        // 输出响应
        return restTemplate.getForObject("/" + serviceName + "/say?message=" + message, String.class);
    }

    @GetMapping("/lb/invoke/{serviceName}/say")// -> /say
    public String lbInvokeSay(@PathVariable String serviceName,
                              @RequestParam String message) {
        // Ribbon RestTemplate 发送请求到服务器
        // 输出响应
        return loadBalancedRestTemplate.getForObject("http://" + serviceName + "/say?message=" + message, String.class);
    }

    @GetMapping("/feign/say")
    public String feignSay(@RequestParam String message) {
        return sayingService.say(message);
    }

    @GetMapping("/rest/say")
    public String restSay(@RequestParam String message) {
        return sayingRestService.say(message);
    }

    @Bean
    public ClientHttpRequestInterceptor interceptor() {
        return new LoadBalancedRequestInterceptor();
    }

    /**
     * 自定义 RestTemplate Bean
     *
     * @param interceptor
     * @return
     */
    @Bean
    @Autowired
    @CustomizedLoadBalanced
    public RestTemplate restTemplate() {// 依赖注入

        return new RestTemplate();
    }

    @Bean
    @Autowired
    public Object customizer(@CustomizedLoadBalanced Collection<RestTemplate> restTemplates,// @Qualifier 不仅仅可以加名称，还可以进行类型过滤
                             ClientHttpRequestInterceptor interceptor) {
        restTemplates.forEach(r -> {
            // 增加拦截器
            r.setInterceptors(Arrays.asList(interceptor));
        });
        return new Object();
    }

    /**
     * Ribbon RestTemplate Bean
     *
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate loadBalancedRestTemplate() {
        return new RestTemplate();
    }
}
