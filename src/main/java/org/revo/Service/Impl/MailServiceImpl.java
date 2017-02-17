package org.revo.Service.Impl;

import org.revo.Domain.User;
import org.revo.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by ashraf on 14/02/17.
 */
@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${vcap.application.uris[0]:http://localhost:8080}")
    String uris;
    @Value("${info.application.name}")
    String appname;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    private void Send(String to, String subject, String text, boolean isHtml) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, isHtml);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        javaMailSender.send(message);
    }

    @Override
    public void SendActivation(User user) {
        Context context = new Context();
        context.setVariable("name", user.getName() + "       " + uris + "/active/" + user.getId());
        context.setVariable("link", uris + "/active/" + user.getId());
        context.setVariable("appname", appname);
        try {
            Send(user.getEmail(), "Activation Message", templateEngine.process("activation", context), true);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }

    }

}
