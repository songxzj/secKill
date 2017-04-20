package org.seckill.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;


public class RestFulUtil {


    private final static Logger logger = LoggerFactory.getLogger(RestFulUtil.class);


    private RestTemplate restTemplate;

    /*@PostConstruct
    public void init(){
         restTemplate = new RestTemplate();

    }*/

    public static void main(String[] args){
        RestTemplate restTemplate1 = new RestTemplate();
        String  cc = restTemplate1.getForObject("http://192.168.10.194:7006/integration/apServer/openServerInfo.do?serverId={id}", String.class, "1000000");

        ResponseEntity<String> res =  restTemplate1.getForEntity("http://192.168.10.194:7006/integration/apServer/openServerInfo.do?serverId={id}", String.class, "1000000");

        System.out.println(res.getBody());
    }


}
