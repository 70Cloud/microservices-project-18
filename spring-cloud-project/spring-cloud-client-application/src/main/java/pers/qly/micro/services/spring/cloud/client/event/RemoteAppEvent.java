package pers.qly.micro.services.spring.cloud.client.event;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 16:47 2019/1/10
 */
public class RemoteAppEvent extends ApplicationEvent {

    /**
     * 事件传输类型：HTTP、RPC、MQ
     */
    private String type;

//    private final String sender;

    /**
     * 应用名称
     */
    private final String appName;

//    /**
//     * 应用实例
//     */
//    private List<ServiceInstance> serviceInstances;

    /**
     * 是否广播到集群
     */
    private final boolean isCluster;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source POJO 事件源 ，Json 格式
     * @param appName
     * @param isCluster
     */
    public RemoteAppEvent(Object source, String appName, boolean isCluster) {
        super(source);
        this.appName = appName;
        this.isCluster = isCluster;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppName() {
        return appName;
    }

}
