package com.pfem2.iso27004.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.pfem2.iso27004.Entity.Evaluation;
import com.pfem2.iso27004.Entity.Indicator;

@Configuration
@EnableScheduling
public class ScheduleTasks {

    private final JavaMailSender emailSender;
    private final EvaluationService evaluationService;
    private final UserService userService;
    private final IndicatorService indicatorService;

    @Autowired
    public ScheduleTasks(JavaMailSender emailSender, EvaluationService evaluationService, UserService userService,
            IndicatorService indicatorService) {
        this.emailSender = emailSender;
        this.evaluationService = evaluationService;
        this.userService = userService;
        this.indicatorService = indicatorService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleFixedDelayTask() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
        List<Evaluation> eval30 = evaluationService.getNextEvaluation(30);
        List<Evaluation> eval15 = evaluationService.getNextEvaluation(15);

        List<Evaluation> evaltoday = evaluationService.getNextEvaluation(0);
        List<Indicator> indicators = indicatorService.getIndicatorNoEval();

        List<String> Emails = this.userService.getAdminEmails();

        if (evaltoday.size() > 0 || indicators.size() > 0) {
            SendImidiateNotice(evaltoday, indicators, Emails);
        }
        sendMail("waildebbaghi@gmail.com", "test", "it works");

        // System.out.println("send seccusful");
    }

    public void SendImidiateNotice(List<Evaluation> evaltoday, List<Indicator> indicators, List<String> emails) {
        String text = "This email serves as a reminder regarding the evaluation of indicators." +
                " We would like to bring the following items to your attention:\n\n";

        if (evaltoday.size() > 0) {
            text += "-Indicators Due for Evaluation Today:\n";

            for (Evaluation evaluation : evaltoday) {
                text += "\t* " + evaluation.getIndicator().getName() + "\n";
            }

        }

        if (indicators.size() > 0) {
            text += "-Indicators Pending Evaluation:\n";

            for (Indicator indicator : indicators) {
                text += "\t* " + indicator.getName() + "\n";
            }

        }

        text += "We kindly request your immediate attention to these matters. Please ensure that the evaluations for the indicators due today are completed as per our guidelines. Additionally, please initiate evaluations for the indicators that have not been evaluated yet."
                + "If you have any questions or need further assistance, please feel free to contact us."
                + "Thank you for your cooperation.";

        for (String email : emails) {
            sendMail(email, "Indicators Due Today and Pending Evaluations", text);
        }

    }

    public void SendEvaluationNotice(List<Evaluation> indicator, List<String> emails, int days) {
        String text = "This email is to remind you that the following indicators are due for evaluation within the next "
                + days + " days: \n\n";

        for (Evaluation evaluation : indicator) {
            text += "* " + evaluation.getIndicator().getName() + "\n";
        }

        text += "Please ensure that the evaluations for these indicators are conducted and completed within the specified timeframe. Your prompt attention to these evaluations is greatly appreciated.\n"
                +
                "If you have any questions or need further information, please feel free to reach out to us.\n" +
                "Thank you for your cooperation.";

        for (String email : emails) {
            sendMail(email, "Indicators Due for Evaluation", text);
        }
    }

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        // message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {

            emailSender.send(message);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("email sending done");

    }

}
