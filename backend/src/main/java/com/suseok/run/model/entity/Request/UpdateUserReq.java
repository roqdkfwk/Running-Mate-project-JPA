package com.suseok.run.model.entity.Request;

import com.suseok.run.model.entity.User;

public class UpdateUserReq {

    private String pw;

    private String userNick;

    private String address;

    public void toEntity(User user) {
        user.setUserPw(this.pw);
        user.setUserNick(this.userNick);
        user.setAddress(this.address);
    }
}
