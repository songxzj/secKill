package org.seckill.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class MailUtil {

    private final static Logger logger = LoggerFactory.getLogger(MailUtil.class);

    @Autowired
    private JavaMailSender javaMailSender;

    private static JavaMailSender mailSender;
    private static SimpleMailMessage message;

    @PostConstruct
    public void init(){
        mailSender = javaMailSender;
        message = new SimpleMailMessage();
    }

    public static void sendEmail(String from, String to, String subject, String text){

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

}
