package com.suseok.run.model.service;

import com.suseok.run.model.dao.EmailVerificationDao;
import com.suseok.run.model.dto.EmailVerification;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final EmailVerificationDao emailVerificationDao;
    private final JavaMailSender javaMailSender;

    /**
     * 이메일 인증 코드 전송
     */
    public String sendVerificationCode(String email) {
        validateEmailFormat(email);

        EmailVerification existingVerification = emailVerificationDao.selectByEmail(email);
        if (existingVerification != null) {
            return "인증번호가 이미 전송되었습니다.";
        }

        String verificationCode = generateVerificationCode();
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(email);
        emailVerification.setVerificationCode(verificationCode);
        emailVerification.setExpiresAt(LocalDateTime.now().plusMinutes(10));

        emailVerificationDao.insertEmailVerification(emailVerification);
        sendEmail(email, verificationCode);

        return "인증번호가 전송되었습니다.";
    }

    /**
     * 이메일 인증 코드 검증
     */
    public String verifyCode(String email, String code) {
        validateEmailFormat(email);

        EmailVerification emailVerification = emailVerificationDao.selectByEmail(email);
        if (emailVerification == null) {
            throw new IllegalArgumentException("해당 이메일에 대한 인증 요청이 없습니다.");
        }

        if (emailVerification.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("인증번호가 만료되었습니다.");
        }

        if (!emailVerification.getVerificationCode().equals(code)) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }

        emailVerificationDao.deleteByEmail(email);
        return "이메일 인증이 완료되었습니다.";
    }

    /**
     * 이메일 인증 코드 재전송
     */
    public String resendVerificationCode(String email) {
        validateEmailFormat(email);

        EmailVerification emailVerification = emailVerificationDao.selectByEmail(email);
        if (emailVerification == null) {
            throw new IllegalArgumentException("해당 이메일에 대한 인증 요청이 없습니다.");
        }

        String newCode = generateVerificationCode();
        emailVerification.setVerificationCode(newCode);
        emailVerification.setExpiresAt(LocalDateTime.now().plusMinutes(10));

        emailVerificationDao.insertEmailVerification(emailVerification);
        sendEmail(email, newCode);

        return "새 인증번호가 전송되었습니다.";
    }

    /**
     * 이메일 유효성 검증
     */
    private void validateEmailFormat(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }
    }

    /**
     * 이메일 인증 코드 생성
     */
    private String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(899999) + 100000); // 6자리 난수 생성
    }

    /**
     * 이메일 전송 로직
     */
    private void sendEmail(String email, String verificationCode) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("이메일 인증 코드");
            helper.setText("인증번호는 다음과 같습니다: " + verificationCode);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송에 실패했습니다.", e);
        }
    }
}
