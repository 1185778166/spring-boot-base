package com.maxt.spring.boot.base.demo.rabbit;

import com.maxt.spring.boot.base.demo.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
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

    @RabbitListener(queues = RabbitMQConfig.NORMAL_QUEUE)
    public void consumer(String msg, Channel channel, Message message) throws IOException {
        System.out.println("队列的消息为："+msg);
        String correlationId = message.getMessageProperties().getCorrelationId();
        System.out.println("唯一标识为："+correlationId);
        //基于消费者实现reject或者nack实现死信效果
        //channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        //自动应答
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        //手动应答，解决发送消息自动确认导致消息丢失问题
        //设置消息的流控,消费者不予应答，不再次给消费者发送消息
        channel.basicQos(1);
        //4. 监听消息
        DeliverCallback callback = (consumerTag, delivery)-> {
            System.out.println("接收到消息:"+new String(message.getBody()));
            //消息标记 envelope.getDeliveryTag()
            //是否批量应答：false代表只应答接收到的那个传递的消息，true代表应答所有消息，包括传递过来的消息
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        boolean autoAck = false;
        channel.basicConsume(RabbitMQConfig.NORMAL_QUEUE, autoAck, callback, consumerTag -> {
            System.out.println("开始监听队列");
        });
    }
}
