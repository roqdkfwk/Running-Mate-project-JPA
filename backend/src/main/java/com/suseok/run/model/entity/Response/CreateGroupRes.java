package com.suseok.run.model.entity.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateGroupRes {

    private String adminName;
    private String groupName;
    private String groupDesc;
}
