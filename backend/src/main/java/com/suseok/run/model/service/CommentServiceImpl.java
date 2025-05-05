package com.suseok.run.model.service;

import com.suseok.run.common.exception.AccessDeniedException;
import com.suseok.run.common.exception.NotFoundException;
import com.suseok.run.model.entity.Comment;
import com.suseok.run.model.entity.Post;
import com.suseok.run.model.entity.Request.CreateCommentReq;
import com.suseok.run.model.entity.Response.CreateCommentRes;
import com.suseok.run.model.entity.User;
import com.suseok.run.model.repository.CommentRepository;
import com.suseok.run.model.repository.PostRespository;
import com.suseok.run.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final PostRespository postRespository;
    private final CommentRepository commentRepository;

    @Override
    public CreateCommentRes createComment(
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
        return CreateCommentRes.fromEntity(comment);
    }

    @Override
    public List<CreateCommentRes> readAllComments(Long postId) {
        List<Comment> commentList = commentRepository.findAllByPost_PostId(postId);

        return commentList.stream()
                .map(CreateCommentRes::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void updateComment(
            Long userSeq,
            Long commentId,
            String content
    ) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("")
        );

        if (!comment.getAuthor().getUserSeq().equals(userSeq)) {
            throw new AccessDeniedException("");
        }

        comment.setContent(content);
        commentRepository.save(comment);
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
