package com.suseok.run.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {
	private int groupId;
	private String groupName;
	private int groupAdmin;
	private Double goalPace;
	private Double goalFrequency;
	private Double goalTotalDistance;
	private Double conPace;
	private Double conFrequency;
	private Double conTotalDistance;
	private Double pace;
	private Double frequency;
	private Double totalDistance;

	// 기본 생성자, 모든 필드 갖는 생성자 삭제

	// getter, setter 삭제

}
