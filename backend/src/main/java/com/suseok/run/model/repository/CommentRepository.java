package com.suseok.run.model.repository;

import com.suseok.run.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost_PostId(Long postId);

    @Query(
            value = "SELECT * FROM comment WHERE post_id = :postId ORDER BY comment_id LIMIT :count",
            nativeQuery = true
    )
    List<Comment> findCommentsNative(
            @Param("postId") Long postId,
            @Param("count") int count
    );
}
