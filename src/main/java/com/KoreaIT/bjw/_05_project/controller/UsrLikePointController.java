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
	public ResultData doLikePoint(String relTypeCode, int relId, String replaceUri) {
		ResultData actorCanMakeLikeRd = likePointService.actorCanMakeLike(rq.getLoginedMemberId(), relTypeCode, relId);
		
		int actorCanMakeLike = (int) actorCanMakeLikeRd.getData1();
		
		if (actorCanMakeLike == 1) {
			ResultData rd = likePointService.deleteLikePoint(rq.getLoginedMemberId(), relTypeCode, relId);
			return ResultData.from("S-1", "찜하기 취소");
		}
	 
		ResultData rd = likePointService.addLikePoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			ResultData.from("F-1", rd.getMsg());
		}

		return ResultData.from("S-3", "찜하기");
	}

	@RequestMapping("/usr/likePoint/doCancelLikePoint")
	@ResponseBody
	public String doCancelLikePoint(String relTypeCode, int relId, String replaceUri) {

		ResultData actorCanMakeLikePoint = likePointService.actorCanMakeLike(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeLikePoint.isSuccess()) {
			return rq.jsHistoryBackOnView(actorCanMakeLikePoint.getMsg());
		}

		ResultData rd = likePointService.deleteLikePoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			rq.jsHistoryBack(rd.getMsg(), "찜하기 취소 실패");
		}

		return rq.jsReplace(rd.getMsg(), replaceUri);
	}

	 

}