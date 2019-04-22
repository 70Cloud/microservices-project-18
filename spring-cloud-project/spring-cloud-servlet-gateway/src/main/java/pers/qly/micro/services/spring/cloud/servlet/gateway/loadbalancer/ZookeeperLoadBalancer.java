package pers.qly.micro.services.spring.cloud.servlet.gateway.loadbalancer;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: NoNo
 * @Description: 自定义实现 ZK 的 LoadBalancer
 * @Date: Create in 23:28 2019/1/9
 */
public class ZookeeperLoadBalancer extends BaseLoadBalancer {

    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();

    @Value("${spring.application.name}")
    private String currentApplicationName;

    private final DiscoveryClient discoveryClient;

    private Map<String, BaseLoadBalancer> loadBalancerMap = new ConcurrentHashMap<>();

    public ZookeeperLoadBalancer(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        updateServers();
    }

    // FIXME 收到了但是还有 404 报错
    @Override
    public Server chooseServer(Object key) {
        if (key instanceof String) {
            String serviceName = String.valueOf(key);
//            BaseLoadBalancer baseLoadBalancer = loadBalancerMap.get(serviceName);
            return loadBalancer.chooseServer(serviceName);
        }
        return super.chooseServer(key);
    }

    /**
     * 更新所有服务器
     */
    @Scheduled(fixedRate = 5000)
    public void updateServers() {
        discoveryClient.getServices().stream()
//                .filter(s -> !currentApplicationName.equals(s))
                .forEach(serviceName -> {

//                    BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
                    loadBalancerMap.put(serviceName, loadBalancer);

                    List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
                    serviceInstances.forEach(serviceInstance -> {
                        Server server = new Server(serviceInstance.isSecure() ? "https://" : "http://",
                                serviceInstance.getHost(), serviceInstance.getPort());
                        loadBalancer.addServer(server);
                    });
                });
    }
}
