package com.maxt.spring.boot.base.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Maxt
 * @Date 2022/4/4 16:07
 * @Version 1.0
 * @Description
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "exchange_boot_name";
    public static final String QUEUE_NAME = "queue_boot_name";
    public static final String ROUTING_KEY = "routing_key_name";

    /**
     * topicExchange容器
     * @return
     */
    @Bean
    public Exchange topicExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).build();
    }

    /**
     * topicQueue容器
     * @return
     */
    @Bean
    public Queue topicQueue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean
    public Binding topicBinding(Exchange topicExchange, Queue topicQueue){
        return BindingBuilder.bind(topicQueue).to(topicExchange).with(ROUTING_KEY).noargs();
    }
}
