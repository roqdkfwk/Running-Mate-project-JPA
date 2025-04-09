package com.suseok.run.model.service;

import com.suseok.run.common.NotFoundException;
import com.suseok.run.model.entity.Group;
import com.suseok.run.model.entity.Post;
import com.suseok.run.model.entity.Request.CreatePostReq;
import com.suseok.run.model.entity.Request.UpdatePostReq;
import com.suseok.run.model.entity.User;
import com.suseok.run.model.repository.PostRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRespository postRepository;

	/**
	 * 게시글 작성
	 * @return
	 */
	@Override
	public Long createPost(
			Long userSeq,
			Long groupId,
			CreatePostReq createPostReq) {
		// 1. 게시글 작성자

		// 2. 게시글을 작성할 게시판(그룹)

		// 3. 게시글 생성 및 저장
		Post post = createPostReq.toEntity(new User(), new Group());
		postRepository.save(post);
		return post.getPostId();
	}

	/**
	 * 게시글 수정
	 */
	@Override
	public void updatePost(
			Long postId,
			UpdatePostReq updatePostReq
	) {
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new NotFoundException("존재하지 않는 게시글입니다.")
		);

		updatePostReq.toEntity(post, updatePostReq);
		postRepository.save(post);
	}

	/**
	 * 게시글 삭제
	 * @param postId
	 * @param userSeq
	 */
	@Override
	public void deletePost(Long postId, Long userSeq) {

	}

	/**
	 * 게시판 내 게시글 목록 조회
	 * @param groupId
	 * @return
	 */
	@Override
	public List<Post> getPostsByGroup(Long groupId) {
		return List.of();
	}

	/**
	 * 게시글 상세 조회
	 * @param postId
	 * @return
	 */
	@Override
	public Post getPost(Long postId) {
		return postRepository.findById(postId).orElseThrow(
				() -> new NotFoundException("존재하지 않는 게시글입니다.")
		);
	}

    /**
     * 게시글 검색
	 * @param con
     * @return
	 */
	@Override
	public List<Post> searchPosts(String con) {

		return new ArrayList<>();
	}

}
