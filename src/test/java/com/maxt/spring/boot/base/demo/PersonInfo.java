package com.maxt.spring.boot.base.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Maxt
 * @Date 2022/3/28 下午6:28
 * @Version 1.0
 * @Description
 */
@Component
@ConfigurationProperties(prefix = "personinfo")
@Data
public class PersonInfo {
    private int age;
    private String name;

}
