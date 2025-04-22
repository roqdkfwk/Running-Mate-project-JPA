package com.suseok.run.model.service;

import com.suseok.run.model.entity.Request.CreateGroupReq;
import com.suseok.run.model.entity.Response.CreateGroupRes;

public interface GroupService {

    CreateGroupRes createGroup(Long userSeq, CreateGroupReq createGroupReq);

    void updateGroup();

    void deleteGroup(Long userSeq, Long groupId);

    void joinGroup(Long userSeq, Long groupId);

    void leaveGroup(Long userSeq, Long groupId);

    void kickMember();
}
