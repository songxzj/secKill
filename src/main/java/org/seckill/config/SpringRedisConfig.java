package org.seckill.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;

@Configuration
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
/*@PropertySources({
        @PropertySource("classpath:config/jdbc.properties"),
        @PropertySource("classpath:config/redis.properties")
})*/
@PropertySource("classpath:config/redis.properties")
public class SpringRedisConfig {

    @Autowired
    Environment environment;

    /**
     * Jedis 配置
     * @return
     */
    @Bean(destroyMethod = "destroy")
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(environment.getProperty("redis.hostName"));
        jedisConnectionFactory.setHostName(environment.getProperty("redis.port"));

        return jedisConnectionFactory;
    }

    /**
     * redisTemplate 配置
     * @param jedisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setHashKeySerializer(stringRedisSerializer());

        return redisTemplate;
    }

    /**
     * 序列化
     * @return
     */
    @Bean
    public StringRedisSerializer stringRedisSerializer(){
        return new StringRedisSerializer();
    }


    /**
     * StringRedisTemplate 配置
     * @param jedisConnectionFactory
     * @return
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory){
        return new StringRedisTemplate(jedisConnectionFactory);
    }


}
