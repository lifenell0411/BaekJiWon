package com.KoreaIT.bjw.BaekJiWon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.bjw.BaekJiWon.service.ReactionPointService;
import com.KoreaIT.bjw.BaekJiWon.vo.ResultData;
import com.KoreaIT.bjw.BaekJiWon.vo.Rq;

@Controller
public class UsrReactionPointController {
	@Autowired
	private ReactionPointService reactionPointService;

	@Autowired
	private Rq rq;

	
	// 좋아요를 실행할 수 있도록 구현
	
	@RequestMapping("usr/reactionPoint/doGoodReaction")
	@ResponseBody
	public ResultData doGoodReaction(String relTypeCode, int relId) {
		
		// 좋아요를 할 수 있는 회원인지 체크
		
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId);
		
		int actorCanMakeReaction = (int) actorCanMakeReactionRd.getData1();
		
		// 이미 좋아요를 했다면 좋아요 취소 될 수 있도록
		
		if (actorCanMakeReaction == 1) {
			ResultData rd = reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			return ResultData.from("S-1", "좋아요 취소");
		}
		
		// 좋아요와 싫어요 둘중에 하나만 클릭할 수 있도록
		
		else if (actorCanMakeReaction == -1) {
			ResultData rd = reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			rd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			return ResultData.from("S-2", "싫어요 누른 상태입니다.");
		}

		ResultData rd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			ResultData.from("F-1", rd.getMsg());
		}

		return ResultData.from("S-3", "좋아요");
	}
	
	
	// 싫어요를 실행할 수 있도록 구현
	
	@RequestMapping("usr/reactionPoint/doBadReaction")
	@ResponseBody
	public ResultData v(String relTypeCode, int relId) {
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId);
		
		
		// 싫어요를 할 수 있는 회원인지 체크 
		
		int actorCanMakeReaction = (int) actorCanMakeReactionRd.getData1();
		
		// 이미 싫어요가 되어있다면 싫어요 취소
		if (actorCanMakeReaction == -1) {
			ResultData rd = reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			return ResultData.from("S-1", "싫어요 취소");
		}
		
		// 좋아요를 누른 상태라면 불가하도록 제어
		else if (actorCanMakeReaction == 1) {
			ResultData rd = reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			rd = reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			return ResultData.from("S-2", "좋아요 누른 상태입니다.");
		}
		
		ResultData rd = reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		if (rd.isFail()) {
			ResultData.from("F-1", rd.getMsg());
		}
		
		return ResultData.from("S-3", "싫어요");
	}

}