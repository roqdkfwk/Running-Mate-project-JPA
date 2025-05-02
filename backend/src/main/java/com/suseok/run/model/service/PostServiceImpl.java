package com.suseok.run.model.service;

import com.suseok.run.common.exception.AccessDeniedException;
import com.suseok.run.common.exception.NotFoundException;
import com.suseok.run.model.entity.Group;
import com.suseok.run.model.entity.Post;
import com.suseok.run.model.entity.Request.CreatePostReq;
import com.suseok.run.model.entity.Request.UpdatePostReq;
import com.suseok.run.model.entity.Response.ReadPostRes;
import com.suseok.run.model.entity.User;
import com.suseok.run.model.repository.GroupRepository;
import com.suseok.run.model.repository.PostRespository;
import com.suseok.run.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRespository postRepository;
	private final UserRepository userRepository;
	private final GroupRepository groupRepository;

	/**
	 * 게시글 작성
	 */
	@Override
	public Long createPost(
			Long userSeq,
			Long groupId,
			CreatePostReq createPostReq
	) {
		// 1. 게시글 작성자
		User author = userRepository.findById(userSeq).orElseThrow(
				() -> new NotFoundException("User not found with id " + userSeq)
		);

		// 2. 게시글을 작성할 게시판(그룹)
		Group group = groupRepository.findById(groupId).orElseThrow(
				() -> new NotFoundException("Group not found with id " + groupId)
		);

		// 3. 게시글 생성 및 저장
		Post post = createPostReq.toEntity(author, group);
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
	 */
	@Override
	public void deletePost(Long userSeq, Long postId) {
		// 1. 게시글 조회
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new NotFoundException("")
		);

		// 2. 게시글 작성자 확인
		if (!post.getAuthor().getUserSeq().equals(userSeq)) {
			throw new AccessDeniedException("");
		}

		// 3. 게시글 삭제
		postRepository.delete(post);
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
	public ReadPostRes getPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new NotFoundException("존재하지 않는 게시글입니다.")
		);

		return ReadPostRes.fromEntity(post);
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
