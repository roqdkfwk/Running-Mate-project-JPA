package com.suseok.run.model.service;

import com.suseok.run.model.entity.Comment;
import com.suseok.run.model.entity.User;
import com.suseok.run.model.repository.CommentRepository;
import com.suseok.run.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public User getUser(Long userSeq) {
        return userRepository.findById(userSeq).orElseThrow(
                () -> new RuntimeException("조회 중 오류 발생")
        );
    }

    public List<Comment> getComments(Long postId, int count) {
        return commentRepository.findCommentsNative(postId, count);
    }
}
