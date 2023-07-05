package com.pfem2.iso27004.Service;

import java.util.ArrayList;
import java.util.Calendar;
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

import com.pfem2.iso27004.Entity.Collector;
import com.pfem2.iso27004.Entity.Evaluation;
import com.pfem2.iso27004.Entity.Indicator;

@Configuration
@EnableScheduling
public class ScheduleTasks {

    private final JavaMailSender emailSender;
    private final UserService userService;
    private final EvaluationService evaluationService;
    private final TemplateEngine templateEngine;

    @Autowired
    public ScheduleTasks(JavaMailSender emailSender, UserService userService, EvaluationService evaluationService,
            TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.userService = userService;
        this.evaluationService = evaluationService;
        this.templateEngine = templateEngine;
    }

    // @Scheduled(cron = "0 0 0 * * ?")
    //
    // @Scheduled(cron = "0 0 9 1,20 * *")
    @Scheduled(fixedDelay = 1000 * 60 * 3)
    public void scheduleFixedDelayTask() {
        List<Collector> collectors = this.userService.getCollectors();
        for (Collector c : collectors) {
            List<Indicator> l = new ArrayList<Indicator>();
            for (Indicator i : c.getIndicator()) {

                Evaluation e = this.evaluationService.getLatestInicatorEvaluation(i.getId());
                if (e != null) {

                    Calendar nextMonth = Calendar.getInstance();
                    nextMonth.add(Calendar.MONTH, 1);
                    Calendar nextEvalDate = Calendar.getInstance();
                    nextEvalDate.setTime(e.getNextEvaluationDate());

                    if (nextEvalDate.get(Calendar.YEAR) == nextMonth.get(Calendar.YEAR)
                            && nextEvalDate.get(Calendar.MONTH) == nextMonth.get(Calendar.MONTH)) {
                        if (!l.contains(i)) {
                            l.add(i);

                        }

                    }
                }
            }
            if (l.size() > 0) {
                Context contxt = new Context();
                contxt.setVariable("deadline", "due for next Mounth");
                contxt.setVariable("indicators", l);
                String body = templateEngine.process("emailTemplate", contxt);

                sendMail(c.getCollector().getEmail(), "Indicators Due for Next Mounth", body);

            }
        }

    }

    /*
     * @Scheduled(cron = "0 0 0 1 * *")
     * public void MounthlyNotice() {
     * List<String> Emails = this.userService.getAdminEmails();
     * List<Indicator> indicatorsM = indicatorService.nextMonthIndicator();
     * if (indicatorsM.size() > 0) {
     *
     * Context contxt = new Context();
     * contxt.setVariable("deadline", "due for next Mounth");
     * contxt.setVariable("indicators", indicatorsM);
     * String body = templateEngine.process("emailTemplate", contxt);
     * for (String email : Emails) {
     * sendMail(email, "Indicators Due for Next Mounth", body);
     * }
     *
     * }
     * }
     *
     * @Scheduled(cron = "0 0 0 * * MON")
     * public void WeeklyNotice() {
     * List<String> Emails = this.userService.getAdminEmails();
     * List<Indicator> indicatorsM = indicatorService.nextWeekIndicator();
     * if (indicatorsM.size() > 0) {
     *
     * Context contxt = new Context();
     * contxt.setVariable("due", true);
     *
     * contxt.setVariable("deadline", "due for next Week");
     * contxt.setVariable("indicators", indicatorsM);
     * String body = templateEngine.process("emailTemplate", contxt);
     * for (String email : Emails) {
     * sendMail(email, "Indicators Due for Next Week", body);
     * }
     *
     * }
     * }
     *
     * @Scheduled(cron = "0 0 9 * * *")
     * public void DailyNotice() {
     * List<String> Emails = this.userService.getAdminEmails();
     * List<Indicator> indicatorso = indicatorService.overdueIndicator();
     * // List<Indicator> indicatorsn = indicatorService.getIndicatorNoEval();
     * Context contxt = new Context();
     * if (indicatorso.size() > 0) {
     *
     * contxt.setVariable("deadline", "over Due");
     * contxt.setVariable("indicators", indicatorso);
     * String body = templateEngine.process("emailTemplate", contxt);
     *
     * for (String email : Emails) {
     * sendMail(email, "Indicators that need Evalaution", body);
     * }
     * }
     * /*
     * if (indicatorsn.size() > 0) {
     * contxt.setVariable("pending", true);
     * contxt.setVariable("notice", "Pending Evaluation");
     * contxt.setVariable("indicators", indicatorsn);
     * }
     */
    /*
     * if (indicatorso.size() > 0 || indicatorsn.size() > 0) {
     * }
     *
     *
     * }
     */

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
