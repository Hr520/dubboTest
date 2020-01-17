package com.xiaoze.api.service;


/**
 * Description: dubbo-springboot
 * <p>
 * Created by az on 2019/10/26/026 16:49
 */
public interface UserService {
    String getUserOne();
    String getUserList();

    String addUser(String username, Integer id);

    String addCacheUser(String username, Integer id);
}
