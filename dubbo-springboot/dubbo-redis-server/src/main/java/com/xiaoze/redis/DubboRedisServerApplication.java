package com.xiaoze.redis;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class DubboRedisServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DubboRedisServerApplication.class, args);
    }

}
