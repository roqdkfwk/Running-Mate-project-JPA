package com.suseok.run.model.repository;

import com.suseok.run.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRespository extends JpaRepository<Post, Long> {

    List<Post> findAllByGroup_GroupId(Long groupId);
}
