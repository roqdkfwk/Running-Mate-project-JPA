package com.suseok.run.model.service;

import com.suseok.run.model.entity.Post;
import com.suseok.run.model.entity.Request.CreatePostReq;
import com.suseok.run.model.entity.Request.UpdatePostReq;
import com.suseok.run.model.entity.Response.ReadPostRes;

import java.util.List;

public interface PostService {

	Long createPost(Long userSeq, Long groupId, CreatePostReq createPostReq);

	ReadPostRes readPost(Long postId);

	List<ReadPostRes> readAllPosts(Long groupId);

	void updatePost(Long postId, UpdatePostReq updatePostReq);

	void deletePost(Long UserSeq, Long postId);

	ReadPostRes getPost(Long postId);

	List<Post> searchPosts(String con);
}
