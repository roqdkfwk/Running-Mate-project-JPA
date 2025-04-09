package com.suseok.run.model.dao;

import com.suseok.run.model.entity.EmailVerification;
import org.apache.ibatis.annotations.Param;

public interface EmailVerificationDao {
    void insertEmailVerification(EmailVerification emailVerification);

    EmailVerification selectByEmail(@Param("email") String email);

    void deleteByEmail(@Param("email") String email);
}

