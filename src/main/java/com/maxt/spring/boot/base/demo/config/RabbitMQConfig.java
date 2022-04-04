package com.maxt.spring.boot.base.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Maxt
 * @Date 2022/4/4 16:07
 * @Version 1.0
 * @Description
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 正常模式的交换机、队列、路由键
     */
    public static final String NORMAL_EXCHANGE = "normal-exchange";
    public static final String NORMAL_QUEUE = "normal-queue";
    public static final String NORMAL_ROUTING_KEY = "normal.#";

    /**
     * 死信模式的交换机、队列、路由键
     */
    public static final String DEAD_EXCHANGE = "dead-exchange";
    public static final String DEAD_QUEUE = "dead-exchange";
    public static final String DEAD_ROUTING_KEY = "dead.#";

    /**
     * 延迟模式的交换机、队列、路由键
     */
    public static final String DELAYED_EXCHANGE = "delayed-exchange";
    public static final String DELAYED_QUEUE = "delayed-queue";
    public static final String DELAYED_ROUTING_KEY = "delayed.#";

    /**
     * 正常交换机Bean
     * @return
     */
    @Bean
    public Exchange topicExchange(){
        return ExchangeBuilder.topicExchange(NORMAL_EXCHANGE).build();
    }

    /**
     * 正常队列Bean
     * @return
     */
    @Bean
    public Queue topicQueue(){
        return QueueBuilder.durable(NORMAL_QUEUE)
                .deadLetterExchange(DEAD_EXCHANGE)
                .deadLetterRoutingKey("dead.abc")
                //设置消息的最大长度
                .maxLength(1)
                .ttl(1000)
                .build();
    }

    /**
     * 正常模式绑定
     * @param topicExchange
     * @param topicQueue
     * @return
     */
    @Bean
    public Binding topicBinding(Exchange topicExchange, Queue topicQueue){
        return BindingBuilder.bind(topicQueue).to(topicExchange).with(NORMAL_ROUTING_KEY).noargs();
    }

    /**
     * 死信交换机Bean
     * @return
     */
    @Bean
    public Exchange deadExchange(){
        return ExchangeBuilder.topicExchange(DEAD_EXCHANGE).build();
    }

    /**
     * 死信队列Bean
     * @return
     */
    @Bean
    public Queue deadQueue(){
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }

    /**
     * 死信模式绑定
     * @param deadExchange
     * @param deadQueue
     * @return
     */
    @Bean
    public Binding deadBinding(Exchange deadExchange, Queue deadQueue){
        return BindingBuilder.bind(deadQueue).to(deadExchange).with(DEAD_ROUTING_KEY).noargs();
    }

    /**
     * 延迟交换机Bean
     * @return
     */
    @Bean
    public Exchange delayedExchange(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "topic");
        Exchange exchange = new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", true, false, arguments);
        return exchange;
    }

    /**
     * 延迟队列Bean
     * @return
     */
    @Bean
    public Queue delayedQueue(){
        return QueueBuilder.durable(DELAYED_QUEUE).build();
    }

    /**
     * 延迟模式绑定
     * @param delayedExchange
     * @param delayedQueue
     * @return
     */
    @Bean
    public Binding delayedBinding(Exchange delayedExchange, Queue delayedQueue){
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }
}
