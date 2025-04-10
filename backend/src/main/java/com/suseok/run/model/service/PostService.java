package com.suseok.run.model.service;

import com.suseok.run.model.entity.Post;
import com.suseok.run.model.entity.Request.CreatePostReq;
import com.suseok.run.model.entity.Request.UpdatePostReq;

import java.util.List;

public interface PostService {

	Long createPost(Long userSeq, Long groupId, CreatePostReq createPostReq);

	void updatePost(Long postId, UpdatePostReq updatePostReq);

	void deletePost(Long UserSeq, Long postId);

	Post getPost(Long postId);

	List<Post> getPostsByGroup(Long groupId);

	List<Post> searchPosts(String con);
}
