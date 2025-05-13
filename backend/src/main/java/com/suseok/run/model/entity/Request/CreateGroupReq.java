package com.suseok.run.model.entity.Request;

import com.suseok.run.model.entity.Group;
import com.suseok.run.model.entity.User;
import lombok.Getter;

@Getter
public class CreateGroupReq {

    private String groupName;
    private String groupDesc;

    public Group toEntity(User admin) {
        return Group.builder()
                .groupName(this.groupName)
                .groupDesc(this.groupDesc)
                .groupAdmin(admin.getUserSeq())
                .build();
    }
}
