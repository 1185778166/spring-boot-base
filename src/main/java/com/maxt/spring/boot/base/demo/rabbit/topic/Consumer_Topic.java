package com.maxt.spring.boot.base.demo.rabbit.topic;

import com.maxt.spring.boot.base.demo.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;

/**
 * @Author Maxt
 * @Date 2022/4/4 16:18
 * @Version 1.0
 * @Description  topic模式消费者
 */
public class Consumer_Topic {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumer(String msg, Channel channel, Message message) throws IOException {
        System.out.println("队列的消息为："+msg);
        String correlationId = message.getMessageProperties().getCorrelationId();
        System.out.println("唯一标识为："+correlationId);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
