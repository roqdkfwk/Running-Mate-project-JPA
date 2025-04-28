package com.suseok.run.model.entity.Response;

import com.suseok.run.model.entity.Group;
import lombok.Getter;

@Getter
public class ReadGroupRes {

    private String groupName;

    public ReadGroupRes(Group group) {
        this.groupName = group.getGroupName();
    }
}
