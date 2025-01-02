package com.suseok.run.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
	private int replyId;
	private int boardId;
	private int writerSeq;
	private String content;
	private Timestamp createdAt;

	// 기본 생성자, 모든 필드 갖는 생성자 삭제
	
	// getter, setter 삭제

}
