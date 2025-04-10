package com.suseok.run.model.repository;

import com.suseok.run.model.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    @Query("SELECT ug FROM UserGroup ug WHERE ug.user.userSeq = :userSeq AND ug.group.groupId = :groupId")
    Optional<UserGroup> findByUserSeqAndGroupId(Long userSeq, Long groupId);
}
