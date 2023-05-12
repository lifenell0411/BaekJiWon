package com.KoreaIT.bjw._05_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.bjw._05_project.service.LikePointService;
import com.KoreaIT.bjw._05_project.vo.ResultData;
import com.KoreaIT.bjw._05_project.vo.Rq;

@Controller
public class UsrLikePointController {
	@Autowired
	private Rq rq;
	@Autowired
	private LikePointService likePointService;
	
	

	@RequestMapping("/usr/likePoint/doLikePoint")
	@ResponseBody
	public String doLikePoint(String relTypeCode, int relId, String replaceUri) {

		ResultData actorCanMakeReactionRd = likePointService.actorCanMakeLike(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeReactionRd.isFail()) {
			return rq.jsHistoryBack("F-1", "이미 했음");
		}

		ResultData rd = likePointService.addLikePoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			rq.jsHistoryBack(rd.getMsg(), "찜하기 실패");
		}

		return rq.jsReplace(rd.getMsg(), replaceUri);
	}

	 

	@RequestMapping("/usr/likePoint/doCancelLikePoint")
	@ResponseBody
	public String doCancelLikePoint(String relTypeCode, int relId, String replaceUri) {

		ResultData actorCanMakeLikePoint = likePointService.actorCanMakeLike(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeLikePoint.isSuccess()) {
			return rq.jsHitoryBackOnView(actorCanMakeLikePoint.getMsg());
		}

		ResultData rd = likePointService.deleteLikePoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			rq.jsHistoryBack(rd.getMsg(), "찜하기 취소 실패");
		}

		return rq.jsReplace(rd.getMsg(), replaceUri);
	}

	 

}