package com.suseok.run.model.dto;

import java.sql.Timestamp;
import lombok.*;

@Getter
@Setter
public class Board {

	private int groupId;
	private int id;
	private int writerSeq;
	private String writerNick;
	private String title;
	private String content;
	private String img;
	private Timestamp createdAt;
	private boolean notice;

	public Board() {
	}

	public Board(int groupId, int id, int writerSeq, String title, String content, String img, Timestamp createdAt,
			boolean notice) {
		this.groupId = groupId;
		this.id = id;
		this.writerSeq = writerSeq;
		this.title = title;
		this.content = content;
		this.img = img;
		this.createdAt = createdAt;
		this.notice = notice;
	}

	// getter, setter 삭제

	public boolean isNotice() {
		return notice;
	}
}
