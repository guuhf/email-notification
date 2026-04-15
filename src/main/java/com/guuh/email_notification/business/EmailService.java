package com.guuh.email_notification.business;

import com.guuh.email_notification.business.dtos.TaskDTO;
import com.guuh.email_notification.infrastructure.exceptions.EmailSendException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${send.email.from}")
    public String from;
    @Value("${send.email.personalName}")
    public String personalName;

    public void sendEmail(TaskDTO taskDTO){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper
                    (message, true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(new InternetAddress(from, personalName));
            mimeMessageHelper.setTo(InternetAddress.parse(taskDTO.getEmail()));
            mimeMessageHelper.setSubject("Task Notification");

            Context context = new Context();
            context.setVariable("taskName", taskDTO.getTaskName());
            context.setVariable("eventDate", taskDTO.getEventDate());
            context.setVariable("email", taskDTO.getEmail());
            context.setVariable("status", "NOTIFIED");
            context.setVariable("description", taskDTO.getDescription());

            String template = templateEngine.process("notificacao", context);
            mimeMessageHelper.setText(template, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new EmailSendException("Failed to send email notification to: " + taskDTO.getEmail() + " Please try again later.");
        }

    }
}
