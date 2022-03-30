package com.maxt.spring.boot.base.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootBaseApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringBootBaseApplication.class);
        springApplication.setBannerMode(Banner.Mode.OFF);

        springApplication.run(args);
    }

}
