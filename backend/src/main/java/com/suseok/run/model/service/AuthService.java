package com.suseok.run.model.service;

import com.suseok.run.model.entity.Request.LoginReq;
import com.suseok.run.model.entity.Response.LoginResult;

public interface AuthService {

    LoginResult login(LoginReq loginReq);
}
