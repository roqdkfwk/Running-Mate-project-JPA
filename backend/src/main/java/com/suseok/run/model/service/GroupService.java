package com.suseok.run.model.service;

import com.suseok.run.model.entity.Request.CreateGroupReq;

public interface GroupService {

    void createGroup(Long userSeq, CreateGroupReq createGroupReq);

    void updateGroup();

    void deleteGroup(Long userSeq, Long groupId);

    void joinGroup();

    void leaveGroup();

    void kickMember();
}
