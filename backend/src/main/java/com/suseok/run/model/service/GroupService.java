package com.suseok.run.model.service;

import com.suseok.run.model.entity.Request.CreateGroupReq;
import com.suseok.run.model.entity.Response.CreateGroupRes;
import com.suseok.run.model.entity.Response.ReadGroupRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupService {

    CreateGroupRes createGroup(Long userSeq, CreateGroupReq createGroupReq);

    Page<ReadGroupRes> getGroupList(Pageable pageable);

    void updateGroup();

    void deleteGroup(Long userSeq, Long groupId);

    void joinGroup(Long userSeq, Long groupId);

    void leaveGroup(Long userSeq, Long groupId);

    void kickMember();
}
