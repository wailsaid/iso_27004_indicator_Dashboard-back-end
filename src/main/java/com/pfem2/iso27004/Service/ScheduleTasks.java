package com.pfem2.iso27004.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleTasks {

    private final JavaMailSender emailSender;

    @Autowired
    public ScheduleTasks(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Scheduled(fixedDelay = 1000 * 60)
    public void scheduleFixedDelayTask() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
        sendSimpleMessage("waildebbaghi@gmail.com", "test", "it works");

        System.out.println("send seccusful");
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        System.out.println("almost");
        try {

            emailSender.send(message);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("done");

    }

}
