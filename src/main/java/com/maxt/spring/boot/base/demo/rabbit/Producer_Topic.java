package com.maxt.spring.boot.base.demo.rabbit;

import com.maxt.spring.boot.base.demo.config.RabbitMQConfig;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author Maxt
 * @Date 2022/4/4 16:18
 * @Version 1.0
 * @Description  topic模式生产者
 */
public class Producer_Topic {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void publish(){
        //设置Confirm机制确保消息一定能够达到Exchange
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack){
                System.out.println("消息已经送到到交换机");
            }else {
                System.out.println("消息没有送达到交换机，需要做一下补偿操作");
            }
        });
        //开启Return机制，确保消息一定可以达到Queue
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            System.out.println("消息："+new String(returnedMessage.getMessage().getBody())+"路由队列失败，需要做补偿操作");
        });
        rabbitTemplate.convertAndSend(RabbitMQConfig.NORMAL_EXCHANGE, "normal.abc", "message", message -> {
            //设置消息持久化
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });
        System.out.println("消息发送成功");
    }

    public void publishWithProps(){
        rabbitTemplate.convertAndSend(RabbitMQConfig.NORMAL_EXCHANGE, "normal.abc", "message", message -> {
            message.getMessageProperties().setClusterId("111");
            //给消息设置生存时间
            message.getMessageProperties().setExpiration("5000");
            //给消息设置延迟时间
            message.getMessageProperties().setDelay(30000);
            return message;
        });
        System.out.println("消息发送成功");
    }
}
