package com.suseok.run.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_seq")
	private Long userSeq;

	@Column(name = "user_id", nullable = false, unique = true)
	private String userId;

	@Column(name = "user_pw", nullable = false)
	private String userPw;

	@Column(name = "user_name", nullable = false)
	private String userName;

	@Column(name = "user_nick", nullable = false, unique = true)
	private String userNick;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	private String address;
	private String img;
	private String phone;
	private boolean exposure;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<UserGroup> userGroupList = new ArrayList<>();

	@OneToOne(mappedBy = "groupAdmin")
	private Group group;

	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	private List<Post> postList = new ArrayList<>();
}
