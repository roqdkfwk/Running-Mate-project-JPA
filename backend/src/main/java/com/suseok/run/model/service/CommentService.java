package com.suseok.run.model.service;

import com.suseok.run.model.entity.Request.CreateCommentReq;

public interface CommentService {

    Long createComent(Long userSeq, Long postId, CreateCommentReq createCommentReq);

    void updateComment(Long userSeq, Long commentId, String content);

    void deleteComment(Long userSeq, Long commentId);
}
