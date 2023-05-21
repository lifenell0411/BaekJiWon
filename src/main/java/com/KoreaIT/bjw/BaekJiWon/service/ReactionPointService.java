package com.KoreaIT.bjw.BaekJiWon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.bjw.BaekJiWon.repository.ReactionPointRepository;
import com.KoreaIT.bjw.BaekJiWon.vo.ResultData;
import com.KoreaIT.bjw.BaekJiWon.vo.Rq;

@Service
public class ReactionPointService {
	@Autowired
	Rq rq;
	@Autowired
	private ReactionPointRepository reactionPointRepository;
	@Autowired
	private ArticleService articleService;

	
	// 특정 회원의 id와 게시판 타입, 게시글번호를 받아 사용자가 리액션을 할 수 있는지 확인하는 메서드
	public ResultData actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		
		// 로그인 여부 체크
		if (actorId == 0) {
			return ResultData.from("F-1", "로그인 후 이용해주세요");
		}
		
		// 사용자가 이미 리액션을 했는지 체크하여 sumReactionPointByMemberId 변수에 int 타입으로 담아둠
		int sumReactionPointByMemberId = reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode,
				relId);

		
		// 0이 아니라면, 즉 이미 리액션 한 회원이라면 리액션불가
		if (sumReactionPointByMemberId != 0) {
			return ResultData.from("F-2", "리액션 불가", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		}
		return ResultData.from("S-1", "리액션 가능", "sumReactionPointByMemberId", sumReactionPointByMemberId);
	}

	
	//  좋아요 리액션 포인트를 추가하는 메서드
	
	public ResultData addGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		int affectedRow = reactionPointRepository.addGoodReactionPoint(actorId, relTypeCode, relId);

		// reactionPoint 테이블에 actorId, relTypeCode, relId를 인자로 전달하여 point 값을 1 증가 후 affectedRow 변수에 담음
		// affectedRow 가 1이 아니라면 좋아요 리액션이 실패됨을 뜻함
		if (affectedRow != 1) {
			return ResultData.from("F-2", "좋아요 실패");
		}

		// relTypeCode(게시글유형)가 article이라면 article 테이블에 있는 GoodReationPoint값을 1증가
		switch (relTypeCode) {
		case "article":
			articleService.increaseGoodReationPoint(relId);
			break;
		}

		return ResultData.from("S-1", "좋아요 처리 됨");

	}
	
	
	//  싫어요 리액션 포인트를 추가하는 메서드
	public ResultData addBadReactionPoint(int actorId, String relTypeCode, int relId) {
		int affectedRow = reactionPointRepository.addBadReactionPoint(actorId, relTypeCode, relId);
		// reactionPoint 테이블에 actorId, relTypeCode, relId를 인자로 전달하여 point 값을 -1로 처리 후 affectedRow 변수에 담음
		// affectedRow 가 1이 아니라면 싫어요 리액션이 실패됨을 뜻함
		if (affectedRow != 1) {
			return ResultData.from("F-2", "싫어요 실패");
		}
		// relTypeCode(게시글유형)가 article이라면 article 테이블에 있는 BadReationPoint값을 1증가
		switch (relTypeCode) {
		case "article":
			articleService.increaseBadReationPoint(relId);
			break;
		}

		return ResultData.from("S-1", "싫어요 처리 됨");
	}
	
	
	// 좋아요 리액션 포인트를 삭제하는 메서드
	public ResultData deleteGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		
		// actorId, relTypeCode, relId를 인자로 받아 좋아요 reactionPoint 값을 삭제
		reactionPointRepository.deleteGoodReactionPoint(actorId, relTypeCode, relId);

		
		// relTypeCode(게시글유형)가 article이라면 article 테이블에 있는 GoodReationPoint값을 1 감소
		switch (relTypeCode) {
		case "article":
			articleService.decreaseGoodReationPoint(relId);
			break;
		}

		return ResultData.from("S-1", "좋아요 취소 처리 됨");
	}

	
	// 싫어요 리액션 포인트를 삭제하는 메서드
	public ResultData deleteBadReactionPoint(int actorId, String relTypeCode, int relId) {
		
		// actorId, relTypeCode, relId를 인자로 받아 싫어요 reactionPoint 값을 삭제
		reactionPointRepository.deleteBadReactionPoint(actorId, relTypeCode, relId);
		// relTypeCode(게시글유형)가 article이라면 article 테이블에 있는 BadReationPoint값을 1 감소
		switch (relTypeCode) {
		case "article":
			articleService.decreaseBadReationPoint(relId);
			break;
		}

		return ResultData.from("S-1", "싫어요 취소 처리 됨");
	}
	
	// detail jsp파일에서 활용하기 위해 구현, 회원이 이미 좋아요를 한 상태인지 확인하는 메서드
	public boolean isAlreadyAddGoodRp(int relId, String relTypeCode) {
		int getPointTypeCodeByMemberId = reactionPointRepository.getSumReactionPointByMemberId(rq.getLoginedMemberId(), relTypeCode, relId);

		if (getPointTypeCodeByMemberId > 0) {
			return true;
		}
		return false;
	}
	// detail jsp파일에서 활용하기 위해 구현, 회원이 이미 싫어요를 한 상태인지 확인하는 메서드
	public boolean isAlreadyAddBadRp(int relId, String relTypeCode) {
		int getPointTypeCodeByMemberId = reactionPointRepository.getSumReactionPointByMemberId(rq.getLoginedMemberId(), relTypeCode, relId);

		if (getPointTypeCodeByMemberId < 0) {
			return true;
		}
		return false;
	}
	

}