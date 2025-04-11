package com.suseok.run.model.service;

import com.suseok.run.common.exception.BadRequestException;
import com.suseok.run.common.exception.ConflictException;
import com.suseok.run.common.exception.NotFoundException;
import com.suseok.run.model.entity.EmailVerification;
import com.suseok.run.model.repository.EmailVerificationRepository;
import com.suseok.run.model.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final JavaMailSender javaMailSender;
    private final String VERIFICATION_CODE = "인증번호";
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    /**
     * 이메일 중복 체크
     */
    public String checkEmailDuplication(String email) {
        validateEmailFormat(email);

        userRepository.findByEmail(email).orElseThrow(
                () -> new ConflictException("이미 사용 중인 이메일입니다.")
        );

        // 인증 번호를 전송하는 과정을 비동기로 수행
        CompletableFuture.runAsync(() -> sendVerificationCode(email));
        return VERIFICATION_CODE + "가 전송되었습니다.";
    }

    /**
     * 이메일 인증번호 전송
     */
    private void sendVerificationCode(String email) {
        // 1. Redis에 인증 번호가 있는 경우 삭제
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        redisTemplate.delete(email);

        // 2. 새로운 인증번호 새성 및 Redis에 저장
        String newVerificationCode = generateVerificationCode();
        valueOps.set(email, newVerificationCode, Duration.ofMinutes(3));

        // 3. 이메일 전송
        sendEmail(email, newVerificationCode);
    }

    /**
     * 이메일 인증번호 검증
     */
    public String verifyCode(String email, String code) {
        validateEmailFormat(email);

        // 1. Redis에서 인증번호 조회
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        String storedCode = valueOps.get(email);

        if (storedCode == null) {
            throw new IllegalArgumentException("해당 이메일에 대한 인증 요청이 없습니다.");
        }

        // 2. 인증번호 일치 여부 확인
        if (!storedCode.equals(code)) {
            throw new BadRequestException(VERIFICATION_CODE + "가 일치하지 않습니다.");
        }

        return "이메일 인증이 완료되었습니다.";
    }

    /**
     * 이메일 인증번호 재전송
     */
    public String resendVerificationCode(String email) {
        validateEmailFormat(email);

        EmailVerification emailVerification = emailVerificationRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("존재하지 않는 이메일입니다.")
        );

        if (emailVerification == null) {
            throw new IllegalArgumentException("해당 이메일에 대한 인증 요청이 없습니다.");
        }

        String newCode = generateVerificationCode();
        emailVerification.setVerificationCode(newCode);
        emailVerification.setExpiresAt(LocalDateTime.now().plusMinutes(10));

        emailVerificationRepository.save(emailVerification);
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
