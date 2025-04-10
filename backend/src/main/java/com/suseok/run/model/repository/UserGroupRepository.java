package com.suseok.run.model.repository;

import com.suseok.run.model.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    Optional<UserGroup> findByUserSeqAndGroupId(Long userSeq, Long groupId);
}
