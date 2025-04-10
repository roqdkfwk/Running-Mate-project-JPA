package com.suseok.run.model.service;

import com.suseok.run.model.entity.Request.CreateCommentReq;

public interface CommentService {

    Long createComent(Long userSeq, Long postId, CreateCommentReq createCommentReq);

    void updateComment();

    void deleteComment();
}
