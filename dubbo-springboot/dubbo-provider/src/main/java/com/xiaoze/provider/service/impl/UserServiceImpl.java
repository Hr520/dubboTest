package com.xiaoze.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.xiaoze.api.service.UserService;
import com.xiaoze.provider.entity.User;
import com.xiaoze.provider.mapper.UserMapper;
import com.xiaoze.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Description: dubbo-springboot
 * <p>
 * Created by az on 2019/10/26/026 16:51
 */
@Service(version = "${demo.service.version}",timeout = 3000)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Reference(version = "${demo.service.version}")
    private RedisService redisService;


    @Override
    public String getUserOne() {
       User user= userMapper.selectById(1);
        return JSON.toJSONString(user);
    }

    @Override
    public String getUserList() {
        List<User> list=userMapper.selectList(null);
         return JSON.toJSONString(list);
    }

    @Override
    public String addUser(String username, Integer id) {
        User user=new User();
        user.setId(id);
        user.setName(username);
        userMapper.insert(user);
        return "success";
    }

    @Override
    public String addCacheUser(String username, Integer id) {
        User user=new User();
        user.setId(id);
        user.setName(username);
        userMapper.insert(user);
         redisService.set(id+"",username);
         return "success";
    }

}
