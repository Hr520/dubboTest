package com.xiaoze.redis.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Service(version = "${demo.service.version}",timeout = 3000)
@Slf4j
@Component
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate redisTemplate;
    private static final RedisSerializer redisSerializer = new StringRedisSerializer();

    private static String getObjectValue(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    @Override
    public String get(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        log.debug("redis get key:{}",key);
        return getObjectValue(value);
    }

    @Override
    public String set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return null;
    }

    @Override
    public String set(String key, String value, int expire) {
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
        return null;
    }

    @Override
    public Map<String, String> hgetAll(String hkey) {
        return redisTemplate.opsForHash().entries(hkey);
    }

    @Override
    public String hget(String hkey, String key) {
        Object value = redisTemplate.opsForHash().get(hkey, key);
        log.debug("redis hget hkey:{},key:{}", hkey, key);
        return getObjectValue(value);
    }

    @Override
    public List<String> hmget(String hkey, List<Object> key) {
        return redisTemplate.opsForHash().multiGet(hkey,key);
    }
    @Override
    public long lpush(String hkey, String... value) {
        log.debug("redis lpush hkey:{}", hkey);
        long string = redisTemplate.opsForList().leftPushAll(hkey, value);
        return string;
    }
    @Override
    public long lpushAll(String hkey, String value) {
        log.debug("redis lpushAll hkey:{}", hkey);
        JSONArray data= JSON.parseArray(value);
        int size=data.size();
        if (size==0){
            return 0L;
        }
        String[] dataList=new String[size];
        for (int i=0;i<size;i++){
            dataList[i]=data.getString(i);
        }
        long string = redisTemplate.opsForList().leftPushAll(hkey, dataList);
        return string;
    }

    @Override
    public long rpush(String hkey, String... value) {
        log.debug("redis rpush hkey:{}", hkey);
        long string = redisTemplate.opsForList().rightPushAll(hkey, value);
        return string;
    }


    @Override
    public String lpop(String hkey) {
        Object value = redisTemplate.opsForList().leftPop(hkey);
        return getObjectValue(value);
    }


    @Override
    public String rpop(String hkey) {
        Object value = redisTemplate.opsForList().rightPop(hkey);
        return getObjectValue(value);
    }

    @Override
    public Set<String> keys(String hkey) {
        return redisTemplate.keys(hkey);
    }

    @Override
    public List<String> lrange(String hkey, int start, int end) {

        return redisTemplate.opsForList().range(hkey, start, end);
    }

    @Override
    public long hset(String hkey, String key, String value) {
        redisTemplate.opsForHash().put(hkey, key, value);
        return 0;
    }

    @Override
    public void hsetAll(String hkey, Map<Object, Object> map) {
        redisTemplate.opsForHash().putAll(hkey, map);
    }

    @Override
    public long incr(String key) {
        Long result = redisTemplate.opsForValue().increment(key, 1L);
        return result;
    }

    @Override
    public long llen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public boolean hexist(String key, String value) {
        log.debug("key:{},value:{}", key, value);
        return redisTemplate.opsForHash().hasKey(key, value);
    }
    @Override
    public long hlen(String key) {

        return 0;
    }

    @Override
    public Set<String> hkeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    @Override
    public boolean expire(String key, int second) {
        log.debug("expire key:{},second:{}", key, second);
        return redisTemplate.expire(key, second, TimeUnit.SECONDS);
    }

    @Override
    public long ttl(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    @Override
    public boolean del(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public long hdel(String hkey, String key) {
        log.debug("hdel hkey:{},key:{}", hkey, key);
        Long result = redisTemplate.opsForHash().delete(hkey, key);
        return result;
    }

    @Override
    public long remove(String key, int action, String value) {
        Long result =  redisTemplate.opsForList().remove(key,action,value);
        return result;
    }


    /**
     * scan 实现
     * @param pattern   表达式
     * @param consumer  对迭代到的key进行操作
     */
    public void scan(String pattern, Consumer<byte[]> consumer) {
        this.redisTemplate.execute((RedisConnection connection) -> {
            try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().count(Long.MAX_VALUE).match(pattern).build())) {
                cursor.forEachRemaining(consumer);
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<String> scanKeys(String pattern) {
        List<String> keys = new ArrayList<>();
        this.scan(pattern, item -> {
            //符合条件的key
            String key = new String(item, StandardCharsets.UTF_8);
            keys.add(key);
        });
        return keys;
    }
}
