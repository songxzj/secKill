package org.seckill.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PropertyUtil {

    @Autowired
    private Environment environment;

    private static Environment env;

    @PostConstruct
    private void init(){
        env = environment;
    }

    public static String getProperty(String key){
        return env.getProperty(key);
    }
}
