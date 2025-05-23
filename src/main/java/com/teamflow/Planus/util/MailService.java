package com.teamflow.Planus.util;

import com.teamflow.Planus.dto.PostDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
@Log4j2
public class MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPostNotification(String to, PostDTO post) {
        try {
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("[새 글 알림] " + post.getTitle());
            Context ctx = new Context();
            ctx.setVariable("post", post);
            String html = templateEngine.process("mail/newPost", ctx);
            helper.setText(html, true);
            mailSender.send(mime);
            log.info("메일 전송 완료: to={}, title={}", to, post.getTitle());
        } catch (MessagingException e) {
            e.printStackTrace(); // or use a logger
        }
    }

    @Async
    public void sendGroupNotify(String to, PostDTO post) {
        try {
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("[그룹 등록] " + post.getTitle());
            Context ctx = new Context();
            ctx.setVariable("post", post);
            String html = templateEngine.process("mail/newGroup", ctx);
            helper.setText(html, true);
            mailSender.send(mime);
            log.info("메일 전송 완료: to={}, title={}", to, post.getTitle());
        } catch (MessagingException e) {
            e.printStackTrace(); // or use a logger
        }
    }

    @Async
    public void sendSignUpNotify(String to, PostDTO post) {
        try {
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("[회원 가입] " + post.getTitle());
            Context ctx = new Context();
            ctx.setVariable("post", post);
            String html = templateEngine.process("mail/newSignUp", ctx);
            helper.setText(html, true);
            mailSender.send(mime);
            log.info("메일 전송 완료: to={}, title={}", to, post.getTitle());
        } catch (MessagingException e) {
            e.printStackTrace(); // or use a logger
        }
    }


}
