package com.suseok.run.model.service;

import com.suseok.run.common.NotFoundException;
import com.suseok.run.model.entity.Group;
import com.suseok.run.model.entity.Request.CreateGroupReq;
import com.suseok.run.model.entity.User;
import com.suseok.run.model.repository.GroupRepository;
import com.suseok.run.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    // Todo: 그룹 생성 후 그룹 상세 페이지로 리다이렉트할 때 보여줄 데이터를 담은 ResponseDTO
    @Override
    public void createGroup(Long userSeq, CreateGroupReq createGroupReq) {
        User admin = userRepository.findById(userSeq).orElseThrow(
                () -> new NotFoundException("존재하지 않는 사용자입니다.")
        );

        Group group = createGroupReq.toEntity(admin);
        groupRepository.save(group);
    }

    @Override
    public void updateGroup() {

    }

    @Override
    public void deleteGroup() {

    }

    @Override
    public void joinGroup() {

    }

    @Override
    public void leaveGroup() {

    }

    @Override
    public void kickMember() {

    }
}
