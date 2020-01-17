package com.xiaoze.redis.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {
    String get(String key);
    String set(String key, String value);
    String set(String key, String value, int expire);
    Map<String, String> hgetAll(String hkey);
    String hget(String hkey, String key);
    List<String> hmget(String hkey, List<Object> key);
    long lpush(String hkey, String... value);
    long lpushAll(String hkey, String value);
    long rpush(String hkey, String... value);
    String lpop(String hkey);
    String rpop(String hkey);
    Set<String> keys(String hkey);

    List<String> lrange(String hkey, int start, int end);
    long hset(String hkey, String key, String value);
    void hsetAll(String hkey, Map<Object, Object> map);

    /**
     * 将 key 中储存的数字值增一
     * @param key
     * @return
     */
    long incr(String key);
    long llen(String key);
    boolean hexist(String key, String value);
    long hlen(String key);
    Set<String> hkeys(String key);

    /**
     * 设置key的生命周期
     * @param key
     * @param second
     * @return
     */
    boolean expire(String key, int second);
    /**
     * 返回key的剩余生命值
     * @param key
     * @return
     */
    long ttl(String key);
    boolean del(String key);
    long hdel(String hkey, String key);
    long remove(String key, int action, String value);
    /**
     *
     * 全局扫描
     * @param
     * @return
     */
    List<String> scanKeys(String pattern);
}
