package org.seckill.config;


import org.seckill.aspectpojo.Audience;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.SecurityConfig;

@Configuration
@Import({SpringDataConfig.class, SpringRedisConfig.class, SecurityConfig.class})
@ComponentScan({"org.seckill.service", "org.seckill.util"})
public class applicationContextConfig {


    /**
     * 切面
     * @return
     */
    @Bean
    public Audience audience(){
        return new Audience();
    }
}
