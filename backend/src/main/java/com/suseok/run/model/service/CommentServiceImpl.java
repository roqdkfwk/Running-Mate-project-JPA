package com.suseok.run.model.service;

import com.suseok.run.common.AccessDeniedException;
import com.suseok.run.common.NotFoundException;
import com.suseok.run.model.entity.Comment;
import com.suseok.run.model.entity.Post;
import com.suseok.run.model.entity.Request.CreateCommentReq;
import com.suseok.run.model.entity.User;
import com.suseok.run.model.repository.CommentRepository;
import com.suseok.run.model.repository.PostRespository;
import com.suseok.run.model.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final PostRespository postRespository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(UserRepository userRepository, PostRespository postRespository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRespository = postRespository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Long createComent(
            Long userSeq,
            Long postId,
            CreateCommentReq createCommentReq
    ) {
        User author = userRepository.findById(userSeq).orElseThrow(
                () -> new NotFoundException("")
        );

        Post post = postRespository.findById(postId).orElseThrow(
                () -> new NotFoundException("")
        );

        Comment comment = createCommentReq.toEntity(author, post);
        commentRepository.save(comment);
        return comment.getCommentId();
    }

    @Override
    public void updateComment() {

    }

    @Override
    public void deleteComment(
            Long userSeq,
            Long commentId
    ) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("")
        );

        if (!comment.getAuthor().getUserSeq().equals(userSeq)) {
            throw new AccessDeniedException("");
        }

        commentRepository.delete(comment);
    }
}
