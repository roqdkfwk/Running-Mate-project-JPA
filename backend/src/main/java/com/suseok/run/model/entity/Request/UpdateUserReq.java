package com.suseok.run.model.entity.Request;

import com.suseok.run.model.entity.User;
import lombok.Getter;

@Getter
public class UpdateUserReq {

    private String userName;
    private String userNick;
    private String address;

    public void toEntity(User user) {
        user.setUserName(this.userName);
        user.setUserNick(this.userNick);
        user.setAddress(this.address);
    }
}
