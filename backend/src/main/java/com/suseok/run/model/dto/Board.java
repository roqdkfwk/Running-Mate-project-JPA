package com.suseok.run.model.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id", nullable = false, unique = true)
	private Long boardId;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	private String img;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	private boolean notice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_seq")
	private User author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private Group group;

	@OneToMany(mappedBy = "board")
	private List<Reply> replyList = new ArrayList<>();

	public boolean isNotice() {
		return notice;
	}
}
