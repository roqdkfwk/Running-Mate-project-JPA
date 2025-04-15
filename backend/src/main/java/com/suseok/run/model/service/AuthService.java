package com.suseok.run.model.service;

import com.suseok.run.model.entity.Request.LoginReq;
import com.suseok.run.model.entity.Response.LoginRes;

public interface AuthService {

    LoginRes login(LoginReq loginReq);
}
