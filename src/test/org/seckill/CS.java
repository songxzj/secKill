package org.seckill;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.Aspect;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.seckill.dao.model.SeckillModel;
import org.seckill.service.SeckillService;
import org.seckill.util.MailUtil;
import org.seckill.util.PropertyUtil;
import org.seckill.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/applicationContext.xml", "classpath:config/spring-mvc.xml"})
public class CS {

    @Autowired
    private SeckillService seckillService;


    @Test
    public void reduceNumber(){
        try {

            SeckillModel sec = seckillService.getById(1000);
            print(sec.getCreateTime());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void cc(){
        seckillService.showSeckill(1000, 1);
    }

    @Test
    public void email(){
        String from = "songxu@ewell.cc";
        String to = "928277484@qq.com";
        String subject = "hello！";
        String text = "你好！";
        MailUtil.sendEmail(from, to, subject, text);
    }

    @Test
    public void redis(){

        print("---------------------");

        RedisUtil.stringSet("num", "34");
        print(RedisUtil.stringGet("num"));
        print(RedisUtil.stringGet("qq") +"---------" + RedisUtil.stringStrlen("qq"));
        print(RedisUtil.stringGet("name") +"---------" + RedisUtil.stringStrlen("name"));
    }


    @Test
    public void property(){
        print("-----------------------------------------------");
        print(PropertyUtil.getProperty("redis.port"));
    }


    @Test
    public void datet(){
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        print("-----------------");
        print(dateFormater.format(date));
    }


    private void print(Object o){
        System.out.println(o);
    }



}
