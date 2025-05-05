package com.suseok.run.model.service;

import com.suseok.run.model.entity.Request.CreateCommentReq;
import com.suseok.run.model.entity.Response.CreateCommentRes;

import java.util.List;

public interface CommentService {

    CreateCommentRes createComment(Long userSeq, Long postId, CreateCommentReq createCommentReq);

    List<CreateCommentRes> readAllComments(Long postId);

    void updateComment(Long userSeq, Long commentId, String content);

    void deleteComment(Long userSeq, Long commentId);
}
