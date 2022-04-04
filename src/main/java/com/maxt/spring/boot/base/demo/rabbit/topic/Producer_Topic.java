package com.maxt.spring.boot.base.demo.rabbit.topic;

import com.maxt.spring.boot.base.demo.config.RabbitMQConfig;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
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
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "exchange_boot_name", "message");
        System.out.println("消息发送成功");
    }

    public void publishWithProps(){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "exchange_boot_name", "message", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setClusterId("111");
                return message;
            }
        });
        System.out.println("消息发送成功");
    }
}
