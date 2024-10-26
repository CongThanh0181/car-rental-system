package vn.com.carrentalsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class SendMailConfirge {
    @Bean
    public JavaMailSender javaMailSender() {
        //Config mail
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);// port TLS

        mailSender.setUsername("ngominhduc240801@gmail.com");// gmail
        mailSender.setPassword("tosfsrybeabujjuf");// app password 2 authentication
        //Properties
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); //config socket TLS
        return mailSender;
    }

}
