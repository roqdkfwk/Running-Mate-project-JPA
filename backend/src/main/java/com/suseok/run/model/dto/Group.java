package com.suseok.run.model.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long groupId;

	@Column(name = "group_name", nullable = false, unique = true)
	private String groupName;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_admin", unique = true)
	private User groupAdmin;

	@OneToMany(mappedBy = "group")
	private List<Board> boards = new ArrayList<>();

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
