package com.KoreaIT.bjw.BaekJiWon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.bjw.BaekJiWon.repository.LikePointRepository;
import com.KoreaIT.bjw.BaekJiWon.vo.ResultData;
import com.KoreaIT.bjw.BaekJiWon.vo.Rq;


// 찜하기 
@Service
public class LikePointService {

	@Autowired
	private LikePointRepository likePointRepository;
	@Autowired
	private ArticleService articleService;
	@Autowired
	Rq rq;
 
	
	// 해당 사용자가 찜하기 클릭이 가능한지 확인, 사용자의 id와 relTypeCode, 게시글번호를 인자로 전달
	public ResultData actorCanMakeLike(int actorId, String relTypeCode, int relId) {
		
		// 로그인 후 찜하기 가능하도록
		if (actorId == 0) {
			return ResultData.from("F-1", "로그인 후 이용 가능합니다");
		}
		// 해당 사용자가 특정 게시글에 이미 찜하기를 했는지 확인
		int sumLikePointByMemberId = likePointRepository.getSumLikePointByMemberId(actorId, relTypeCode,
				relId);

		if (sumLikePointByMemberId != 0) {
			return ResultData.from("F-2", "찜하기 불가", "sumLikePointByMemberId", sumLikePointByMemberId);
		}
		return ResultData.from("S-1", "찜하기 가능", "sumLikePointByMemberId", sumLikePointByMemberId);
	}

	// 찜하기 처리, 사용자의 id와 relTypeCode, 게시글번호를 인자로 전달
	public ResultData addLikePoint(int actorId, String relTypeCode, int relId) {
		int affectedRow = likePointRepository.addLikePoint(actorId, relTypeCode, relId);

		if (affectedRow != 1) {
			return ResultData.from("F-2", "찜하기 실패");
		}

		
		// relTypeCode가 article인 경우 likePoint를 증가
		switch (relTypeCode) {
		case "article":
			articleService.increaseLikePoint(relId);
			break;
		}

		return ResultData.from("S-1", "찜하기 처리 되었습니다");

	}

	 

	// 찜하기 취소처리, 사용자의 id와 relTypeCode, 게시글번호를 인자로 전달
	public ResultData deleteLikePoint(int actorId, String relTypeCode, int relId) {
		likePointRepository.deleteLikePoint(actorId, relTypeCode, relId);

		
		// relTypeCode가 article인 경우 likePoint를 감소
		switch (relTypeCode) {
		case "article":
			articleService.decreaseLikePoint(relId);
			break;
		}

		return ResultData.from("S-1", "찜하기 취소 처리 되었습니다");
	}

	
	// 로그인한 멤버가 이미 좋아요를 추가한 경우 true를, 좋아요를 추가하지 않은 경우 false 리턴
	public boolean isAlreadyAddLikeRp(int relId, String relTypeCode) {
		int getPointTypeCodeByMemberId = likePointRepository.getSumLikePointByMemberId(rq.getLoginedMemberId(), relTypeCode, relId);

		if (getPointTypeCodeByMemberId > 0) {
			return true;
		}
		return false;
	}

	 
}