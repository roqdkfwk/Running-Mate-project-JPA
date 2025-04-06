package com.suseok.run.model.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_seq")
	private Long userSeq;

	@Column(name = "user_id", nullable = false, unique = true)
	private String userId;

	@Column(name = "user_pwd", nullable = false)
	private String userPwd;

	@Column(name = "user_name", nullable = false)
	private String userName;

	@Column(name = "user_nick", nullable = false, unique = true)
	private String userNick;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	private String address;
	private String img;
	private String phone;
	private boolean exposure;

	@ManyToMany(mappedBy = "members")
	private List<Group> groupList = new ArrayList<>();

	@OneToOne(mappedBy = "groupAdmin")
	private Group group;

	@OneToMany(mappedBy = "author")
	private List<Board> boardList = new ArrayList<>();
}
