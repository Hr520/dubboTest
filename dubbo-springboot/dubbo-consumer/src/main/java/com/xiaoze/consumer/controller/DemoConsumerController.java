package com.xiaoze.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xiaoze.api.service.DemoService;
import com.xiaoze.api.service.UserService;
import com.xiaoze.redis.service.RedisService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * DemoConsumerController
 * 消费者控制层
 * @author xiaoze
 * @date 2018/6/7
 */
@RestController
public class DemoConsumerController {

    @Reference(version = "${demo.service.version}")
    private DemoService demoService;


    @Reference(version = "${demo.service.version}")
    private UserService userService;

    @Reference(version = "${demo.service.version}")
    private RedisService redisService;

    @RequestMapping("/sayHello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return demoService.sayHello(name);
    }


    @RequestMapping("/getUser")
    public String getUser() {
        return userService.getUserOne();
    }

    @PostMapping("/addCache")
    public  String addCache(String username,String key){
        redisService.set(key,username);
        return "success";
    }

    @PostMapping("addUser")
    public  String addUser(String username,Integer id) {
     return    userService.addUser(username,id);
    }

    @PostMapping("addCacheUser")
    public  String addCacheUser(String username,Integer id) {
        return    userService.addCacheUser(username,id);
    }
}
