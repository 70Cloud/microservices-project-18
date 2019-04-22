package pers.qly.micro.services.mvc.service;

import pers.qly.micro.services.mvc.annotation.TransactionService;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 21:18 2019/1/7
 */
@TransactionService(value = "echoService-2018",txName = "myTxName") // @Service Bean + @Transactional
// 定义它的名称
public class EchoService {

    public void echo(String message){
        System.out.println(message);
    }
}
