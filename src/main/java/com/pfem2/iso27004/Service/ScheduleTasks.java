package com.pfem2.iso27004.Service;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.pfem2.iso27004.Entity.Indicator;

@Configuration
@EnableScheduling
public class ScheduleTasks {

    private final JavaMailSender emailSender;
    private final UserService userService;
    private final IndicatorService indicatorService;
    private final TemplateEngine templateEngine;

    @Autowired
    public ScheduleTasks(JavaMailSender emailSender, UserService userService, IndicatorService indicatorService,
            TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.userService = userService;
        this.indicatorService = indicatorService;
        this.templateEngine = templateEngine;
    }

    // @Scheduled(cron = "0 0 0 * * ?")
    // @Scheduled(fixedDelay = 1000 * 60 * 60)
    @Scheduled(cron = "0 0 9 1,20 * *")
    public void scheduleFixedDelayTask() {
        /*
         * List<String> Emails = this.userService.getAdminEmails();
         * // System.out.println( "Fixed delay task - " + System.currentTimeMillis() /
         * // 1000);
         *
         * List<Indicator> indicatorsW = indicatorService.nextWeekIndicator();
         * if (indicatorsW.size() > 0) {
         *
         * }
         * List<Indicator> indicatorsOverDue = indicatorService.overdueIndicator();
         * if (indicatorsOverDue.size() > 0) {
         *
         * }
         *
         * // List<Evaluation> eval15 = evaluationService.getNextEvaluation(15);
         *
         * // List<Evaluation> evaltoday = evaluationService.getNextEvaluation(0);
         * List<Indicator> indicators = indicatorService.getIndicatorNoEval();
         * if (indicators.size() > 0) {
         * SendImidiateNotice(indicators, Emails);
         * }
         *
         * // System.out.println("send seccusful");
         */
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void MounthlyNotice() {
        List<String> Emails = this.userService.getAdminEmails();
        List<Indicator> indicatorsM = indicatorService.nextMonthIndicator();
        if (indicatorsM.size() > 0) {

            Context contxt = new Context();
            contxt.setVariable("deadline", "due for next Mounth");
            contxt.setVariable("indicators", indicatorsM);
            String body = templateEngine.process("emailTemplate", contxt);
            for (String email : Emails) {
                sendMail(email, "Indicators Due for Next Mounth", body);
            }

        }
    }

    @Scheduled(cron = "0 0 0 * * MON")
    public void WeeklyNotice() {
        List<String> Emails = this.userService.getAdminEmails();
        List<Indicator> indicatorsM = indicatorService.nextWeekIndicator();
        if (indicatorsM.size() > 0) {

            Context contxt = new Context();
            contxt.setVariable("due", true);

            contxt.setVariable("deadline", "due for next Week");
            contxt.setVariable("indicators", indicatorsM);
            String body = templateEngine.process("emailTemplate", contxt);
            for (String email : Emails) {
                sendMail(email, "Indicators Due for Next Week", body);
            }

        }
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void DailyNotice() {
        List<String> Emails = this.userService.getAdminEmails();
        List<Indicator> indicatorso = indicatorService.overdueIndicator();
        // List<Indicator> indicatorsn = indicatorService.getIndicatorNoEval();
        Context contxt = new Context();
        if (indicatorso.size() > 0) {

            contxt.setVariable("deadline", "over Due");
            contxt.setVariable("indicators", indicatorso);
            String body = templateEngine.process("emailTemplate", contxt);

            for (String email : Emails) {
                sendMail(email, "Indicators that need Evalaution", body);
            }
        }
        /*
         * if (indicatorsn.size() > 0) {
         * contxt.setVariable("pending", true);
         * contxt.setVariable("notice", "Pending Evaluation");
         * contxt.setVariable("indicators", indicatorsn);
         * }
         */
        /*
         * if (indicatorso.size() > 0 || indicatorsn.size() > 0) {
         * }
         */

    }

    public void sendMail(String to, String subject, String body) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);

            ClassPathResource imageResource = new ClassPathResource("static/images/logo.png");
            messageHelper.addInline("logo", imageResource);

            messageHelper.setText(body, true); // Set the HTML content and enable HTML rendering

            // Send the email

            emailSender.send(mimeMessage);
            // emailSender.send(message);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("email : '" + subject + "' to '" + to + "' send.");

    }

}
