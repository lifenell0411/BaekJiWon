package com.KoreaIT.bjw.BaekJiWon.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private String title;
	private String body;
	private int hitCount;
	private int goodReactionPoint;
	private int badReactionPoint;
	private int boardId;
	private int likePoint;
	private int doCancelLikePoint;
	private String extra__writer;
	private int point;

	private boolean actorCanModify;
	private boolean actorCanDelete;

}