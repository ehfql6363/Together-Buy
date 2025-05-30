package com.ssafy.TogetherBuyMain.security.email.emailService;

import com.ssafy.TogetherBuyMain.global.exception.BusinessLogicException;
import com.ssafy.TogetherBuyMain.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender emailSender;

    private static final String TITLE = "[DNA] Email Verification Code";
    private static final String TEXT_PREFIX = "Please copy and enter the email verification code listed below.\nVerification Code: ";

    public void sendEmail(String email, String code) {
        String text = TEXT_PREFIX + code;
        SimpleMailMessage emailForm = createEmailForm(email, text);
        log.info("SimpleMailMessage {}", emailForm);
        try {
            emailSender.send(emailForm);
        } catch (RuntimeException e) {
            throw new BusinessLogicException(ExceptionCode.SEND_MAIL_FAILED);
        }
    }

    private SimpleMailMessage createEmailForm(String email, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(TITLE);
        message.setText(text);
        return message;
    }
}