package com.KoreaIT.bjw._05_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.bjw._05_project.repository.LikePointRepository;
import com.KoreaIT.bjw._05_project.vo.ResultData;
import com.KoreaIT.bjw._05_project.vo.Rq;

@Service
public class LikePointService {

	@Autowired
	private LikePointRepository likePointRepository;
	@Autowired
	private ArticleService articleService;
	@Autowired
	Rq rq;
////////////////////////////////////////////////////////////////////////////////
	
	public ResultData actorCanMakeLike(int actorId, String relTypeCode, int relId) {
		if (actorId == 0) {
			return ResultData.from("F-1", "로그인 하고 오렴");
		}
		int sumLikePointByMemberId = likePointRepository.getSumLikePointByMemberId(actorId, relTypeCode,
				relId);

		if (sumLikePointByMemberId != 0) {
			return ResultData.from("F-2", "찜 불가", "sumLikePointByMemberId", sumLikePointByMemberId);
		}
		return ResultData.from("S-1", "찜 가능", "sumLikePointByMemberId", sumLikePointByMemberId);
	}

	public ResultData addLikePoint(int actorId, String relTypeCode, int relId) {
		int affectedRow = likePointRepository.addLikePoint(actorId, relTypeCode, relId);

		if (affectedRow != 1) {
			return ResultData.from("F-2", "찜하기 실패");
		}

		switch (relTypeCode) {
		case "article":
			articleService.increaseLikePoint(relId);
			break;
		}

		return ResultData.from("S-1", "찜하기 처리 됨");

	}

	 

	public ResultData deleteLikePoint(int actorId, String relTypeCode, int relId) {
	    likePointRepository.deleteLikePoint(actorId, relTypeCode, relId);
	    int affectedRow = likePointRepository.cancelLikePoint(actorId, relTypeCode, relId);
	    if (affectedRow != 1) {
	        return ResultData.from("F-2", "찜하기 취소 실패");
	    }
	    switch (relTypeCode) {
	        case "article":
	            articleService.decreaseLikePoint(relId);
	            break;
	        // 다른 relTypeCode 값에 대한 처리 추가 가능
	    }

	    return ResultData.from("S-1", "찜 취소 처리 됨");
	}

	public Object isAlreadyAddLikeRp(int relId, String relTypeCode) {
		int getPointTypeCodeByMemberId = likePointRepository.getSumLikePointByMemberId(rq.getLoginedMemberId(), relTypeCode, relId);

		if (getPointTypeCodeByMemberId > 0) {
			return true;
		}
		return false;
	}

	public ResultData cancelLikePoint(int actorId, String relTypeCode, int relId) {
		int affectedRow = likePointRepository.cancelLikePoint(actorId, relTypeCode, relId);

		if (affectedRow != 1) {
			return ResultData.from("F-2", "찜하기 취소 실패");
		}

		switch (relTypeCode) {
		case "article":
			articleService.decreaseLikePoint(relId);
			break;
		}

		return ResultData.from("S-1", "싫어요 처리 됨");
	}

}