package org.seckill.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private final static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private RedisTemplate redisTemplate;

    private static RedisTemplate<String, String> redisTemp;
    private static ValueOperations<String, String>  valueTemp;
    private static ListOperations<String, String > listTemp;
    private static HashOperations<String, String, String> hashTemp;
    private static SetOperations<String, String> setTemp;

    @PostConstruct
    private void init(){
        redisTemp = redisTemplate;
        valueTemp = redisTemplate.opsForValue();
        listTemp = redisTemplate.opsForList();
        hashTemp = redisTemplate.opsForHash();
        setTemp = redisTemplate.opsForSet();
    }

    /**
     * 删除单个 key
     * @param key
     */
    public static void del(String key){
        redisTemp.delete(key);
    }

    /**
     * 删除多个 key
     * @param keys
     */
    public static void del(List<String> keys){
        redisTemp.delete(keys);
    }

    /**
     * key 存在, value 追加
       key 不存在， 执行 SET 操作一样
     * @param key
     * @param value
     * @return
     */
    public static Integer stringAppend(String key, String value){
        return valueTemp.append(key, value);
    }

    /**
     * 返回 key 所关联的字符串值
     * @param key
     * @return
     */
    public static String stringGet(String key){
        return valueTemp.get(key);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值
     * @param key
     * @param value
     */
    public static String stringGetSet(String key, String value) {
        return valueTemp.getAndSet(key, value);
    }

    /**
     * 返回所有(一个或多个)给定 key 的值
     * @param keys
     * @return
     */
    public static List<String> stringMGet(List<String> keys){
        return valueTemp.multiGet(keys);
    }

    /**
     * 同时设置一个或多个 key-value 对
     如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值
     * @param map
     */
    public static void stringMSet(Map<String, String> map){
        valueTemp.multiSet(map);
        valueTemp.multiSetIfAbsent(map);
    }

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在
       若给定的 key 已经存在，则 SETNX 不做任何动作
     * @param key
     * @param value
     * @return
     */
    public static boolean stringSetNX(String key, String value){
        return valueTemp.setIfAbsent(key, value);
    }
    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
       即使只有一个给定 key 已存在， MSETNX 也会拒绝执行所有给定 key 的设置操作
     * @param map
     */
    public static boolean stringMSetNX(Map<String, String> map){
        return valueTemp.multiSetIfAbsent(map);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的生存时间设为 timeout (以秒为单位)
       如果 key 已经存在， SETEX 命令将覆写旧值
     * @param key
     * @param value
     * @param timeout
     */
    public static void stringSetEX(String key, String value, long timeout){
        valueTemp.set(key, value, timeout);
    }

    /**
     * 同SETEX，但它以毫秒为单位设置 key 的生存时间
     * @param key
     * @param value
     * @param timeout
     */
    public static void stringPSetEX(String key, String value, long timeout){
        valueTemp.set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 将字符串值 value 关联到 key
       如果 key 已经持有其他值，SET 就覆写旧值，无视类型。
       对于某个原本带有生存时间（TTL）的键来说，当 SET 命令成功在这个键上执行时，这个键原有的 TTL 将被清除。
     * @param key
     * @param value
     */
    public static void stringSet(String key, String value) {
        valueTemp.set(key, value);
    }

    /**
     * 返回 key 所储存的字符串值的长度
     * @param key
     * @return
     */
    public static long stringStrlen(String key){
        return valueTemp.size(key);
    }







}
