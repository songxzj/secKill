package org.seckill.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SpringDataConfig.class, SpringRedisConfig.class})
@ComponentScan({"org.seckill.service", "org.seckill.util"})
public class applicationContextConfig {
}
