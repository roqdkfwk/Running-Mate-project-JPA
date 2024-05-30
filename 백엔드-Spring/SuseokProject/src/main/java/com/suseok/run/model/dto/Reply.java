package com.suseok.run.model.dto;

import java.sql.Timestamp;

public class Reply {
	private int replyId;
	private int boardId;
	private int writerSeq;
	private String content;
	private Timestamp createdAt;

	public Reply() {
	}

	public Reply(int replyId, int boardId, int writerSeq, String content, Timestamp createdAt) {
		super();
		this.replyId = replyId;
		this.boardId = boardId;
		this.writerSeq = writerSeq;
		this.content = content;
		this.createdAt = createdAt;
	}


	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getWriterSeq() {
		return writerSeq;
	}

	public void setWriterSeq(int writerSeq) {
		this.writerSeq = writerSeq;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

}
