package com.suseok.run.model.service;

import com.suseok.run.model.entity.Request.CreateCommentReq;
import com.suseok.run.model.entity.Response.CreateCommentRes;
import com.suseok.run.model.entity.Response.ReadCommentRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    CreateCommentRes createComment(Long userSeq, Long postId, CreateCommentReq createCommentReq);

    List<CreateCommentRes> readAllComments(Long postId);

    Page<ReadCommentRes> getCommentList(Pageable pageable);

    void updateComment(Long userSeq, Long commentId, String content);

    void deleteComment(Long userSeq, Long commentId);
}
