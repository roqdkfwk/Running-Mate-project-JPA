package com.suseok.run.model.service;

import com.suseok.run.common.ConflictException;
import com.suseok.run.model.dao.EmailVerificationDao;
import com.suseok.run.model.dto.EmailVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final EmailVerificationDao emailVerificationDao;
    private final JavaMailSender javaMailSender;
    private final String VERIFICATION_CODE = "인증번호";
    private final UserService userService;

    /**
     * 이메일 중복 체크
     */
    public String checkEmailDuplication(String email) {
        validateEmailFormat(email);

        if (userService.findByEmail(email) != null) {
            throw new ConflictException("이미 사용 중인 이메일입니다.");
        }

        // 인증 번호를 전송하는 과정을 비동기로 수행
        CompletableFuture.runAsync(() -> sendVerificationCode(email));

        return VERIFICATION_CODE + "가 전송되었습니다.";
    }

    /**
     * 이메일 인증번호 전송
     */
    private void sendVerificationCode(String email) {
        // 1. 기존에 저장된 인증번호가 있는 경우 삭제
        EmailVerification emailVerification = emailVerificationDao.selectByEmail(email);
        if (emailVerification != null) {
            emailVerificationDao.deleteByEmail(email);
        }

        // 2. 새로운 인증번호 생성 및 저장
        String newVerificationCode = generateVerificationCode();
        emailVerification = new EmailVerification();
        emailVerification.setEmail(email);
        emailVerification.setVerificationCode(newVerificationCode);
        emailVerification.setExpiresAt(LocalDateTime.now().plusMinutes(3));
        emailVerificationDao.insertEmailVerification(emailVerification);

        // 3. 이메일 전송
        sendEmail(email, newVerificationCode);
    }

    /**
     * 이메일 인증번호 검증
     */
    public String verifyCode(String email, String code) {
        validateEmailFormat(email);

        // 1. 데이터베이스에서 이메일로 인증번호 조회
        EmailVerification emailVerification = emailVerificationDao.selectByEmail(email);
        if (emailVerification == null) {
            throw new IllegalArgumentException("해당 이메일에 대한 인증 요청이 없습니다.");
        }

        // 2. 인증번호 유효성 확인
        if (emailVerification.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(VERIFICATION_CODE + "가 만료되었습니다.");
        }

        // 3. 인증번호
        if (!emailVerification.getVerificationCode().equals(code)) {
            throw new IllegalArgumentException(VERIFICATION_CODE + "가 일치하지 않습니다.");
        }

        // 4. 인증 완료 후 인증번호 삭제
        emailVerificationDao.deleteByEmail(email);
        return "이메일 인증이 완료되었습니다.";
    }

    /**
     * 이메일 인증번호 재전송
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

        return "새 " + VERIFICATION_CODE + "가 전송되었습니다.";
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
     * 이메일 인증번호 생성
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
            helper.setSubject("회원가입 " + VERIFICATION_CODE + " 안내");
            helper.setText(VERIFICATION_CODE + " : " + verificationCode);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송에 실패했습니다.", e);
        }
    }
}
