package com.xiaoze.dubbo;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.auth.login.Configuration;

/**
 * Description: dubbo-springboot
 * <p>
 * Created by az on 2019/10/26/026 19:18
 */
@EnableDubbo
@SpringBootApplication
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(Configuration.class,args);
    }
}
