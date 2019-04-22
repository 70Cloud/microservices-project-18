package pers.qly.micro.services.mvc.reactive.loader;

import java.util.concurrent.*;

/**
 * @Author: NoNo
 * @Description: 模拟并行执行
 * 判断是不是阻塞，就看有没有抛出 InterruptedException,{@link Future#get()}
 * 还可以看方法是否回调
 * @Date: Create in 22:30 2019/1/7
 */
public class ParallelDataLoader extends DataLoader {

    protected void doLoad() {  // 并行计算
        ExecutorService executorService = Executors.newFixedThreadPool(3); // 创建线程池

        //CompletionService队列，一旦有数据才返回
        CompletionService completionService = new ExecutorCompletionService(executorService);
        completionService.submit(super::loadConfigurations, null);      //  耗时 >= 1s
        completionService.submit(super::loadUsers, null);               //  耗时 >= 2s
        completionService.submit(super::loadOrders, null);              //  耗时 >= 3s

        int count = 0;
        while (count < 3) { // 等待三个任务完成
            if (completionService.poll() != null) { // poll 有数才返回
                count++;
            }
        }
        executorService.shutdown();
    }  // 总耗时 max(1s, 2s, 3s)  >= 3s

    public static void main(String[] args) {
        new ParallelDataLoader().load();
    }
}
