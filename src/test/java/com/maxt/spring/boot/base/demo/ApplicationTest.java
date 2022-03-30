package com.maxt.spring.boot.base.demo;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author Maxt
 * @Date 2022/3/28 下午6:21
 * @Version 1.0
 * @Description 配置测试类
 */
@SpringBootTest
//@RunWith(SpringRunner.class)
public class ApplicationTest {

    //获取配置文件中的age
    @Value("${age}")
    private int age;
    //获取配置文件中的name
    @Value("${name}")
    private String name;

    @Autowired
    private PersonInfo personInfo;

    @Test
    public void tesApplication(){
        System.out.println(age);
        System.out.println(name);
        System.out.println(personInfo.getAge());
        System.out.println(personInfo.getName());
    }
}
