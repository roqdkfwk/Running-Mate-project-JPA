package com.suseok.run.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`group`")
@Builder
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long groupId;

	@Column(name = "group_name", nullable = false, unique = true)
	private String groupName;

	@Column(name = "group_desc")
	private String groupDesc;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_admin", unique = true)
	private User groupAdmin;

	@OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
	private List<UserGroup> userGroupList = new ArrayList<>();

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();

	private Double goalPace;
	private Double goalFrequency;
	private Double goalTotalDistance;
	private Double conPace;
	private Double conFrequency;
	private Double conTotalDistance;
	private Double pace;
	private Double frequency;
	private Double totalDistance;
}
