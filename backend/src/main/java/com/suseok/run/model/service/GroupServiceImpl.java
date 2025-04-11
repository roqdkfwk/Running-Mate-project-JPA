package com.suseok.run.model.service;

import com.suseok.run.common.exception.AccessDeniedException;
import com.suseok.run.common.exception.NotFoundException;
import com.suseok.run.model.entity.Group;
import com.suseok.run.model.entity.Request.CreateGroupReq;
import com.suseok.run.model.entity.User;
import com.suseok.run.model.entity.UserGroup;
import com.suseok.run.model.repository.GroupRepository;
import com.suseok.run.model.repository.UserGroupRepository;
import com.suseok.run.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    // Todo: 트랜잭션 적용

    // Todo: 의존성 고민
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

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

    // Todo: 그룹 정보 수정 페이지에서만 그룹 삭제 버튼을 볼 수 있도록 설정, 그룹이 삭제되면 연관 데이터 모두 제거
    // Todo: 작성한 게시글의 흔적을 볼 수 있도록 설정할지 고려
    @Override
    public void deleteGroup(Long userSeq, Long groupId) {
        // 1. 사용자 조회
        User user = userRepository.findById(userSeq).orElseThrow(
                () -> new NotFoundException("존재하지 않는 사용자입니다.")
        );

        // 2. 그룹 조회
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 그룹입니다.")
        );

        // 3. 관리자 여부 확인
        if (!group.getGroupAdmin().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException("그룹의 관리자만 삭제할 수 있습니다.");
        }

        // 4. 그룹 삭제
        groupRepository.delete(group);
    }

    // Todo: 그룹 가입 기능은 GroupService or UserService?
    @Override
    public void joinGroup(Long userSeq, Long groupId) {
        // 1. 사용자 조회
        User user = userRepository.findById(userSeq).orElseThrow(
                () -> new NotFoundException("존재하지 않는 사용자입니다.")
        );

        // 2. 그룹 조회
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 그룹입니다.")
        );

        // 3. 그룹에 가입
        UserGroup userGroup = new UserGroup(null, user, group);
        userGroupRepository.save(userGroup);
    }

    // Todo: 그룹 탈퇴 기능은 GroupService or UserService?
    @Override
    public void leaveGroup(Long userSeq, Long groupId) {
        UserGroup userGroup = userGroupRepository.findByUserSeqAndGroupId(userSeq, groupId).orElseThrow(
                () -> new NotFoundException("")
        );

        userGroupRepository.delete(userGroup);
    }

    @Override
    public void kickMember() {

    }
}
