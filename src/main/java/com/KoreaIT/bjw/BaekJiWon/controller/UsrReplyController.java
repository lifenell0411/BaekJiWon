package com.KoreaIT.bjw.BaekJiWon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.bjw.BaekJiWon.service.ArticleService;
import com.KoreaIT.bjw.BaekJiWon.service.ReplyService;
import com.KoreaIT.bjw.BaekJiWon.util.Ut;
import com.KoreaIT.bjw.BaekJiWon.vo.Article;
import com.KoreaIT.bjw.BaekJiWon.vo.Reply;
import com.KoreaIT.bjw.BaekJiWon.vo.ResultData;
import com.KoreaIT.bjw.BaekJiWon.vo.Rq;

@Controller
public class UsrReplyController {

	@Autowired
	private Rq rq;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private ArticleService articleService;

	
	// 댓글 작성 구현
	@RequestMapping("/usr/reply/doWrite")
	@ResponseBody
	public String doWrite(String relTypeCode, int relId, String body, String replaceUri) {

		if (Ut.empty(relTypeCode)) {
			return rq.jsHistoryBack("F-1", "relTypeCode 을(를) 입력해주세요");
		}
		if (Ut.empty(relId)) {
			return rq.jsHistoryBack("F-2", "relId 을(를) 입력해주세요");
		}
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("F-3", "body 을(를) 입력해주세요");
		}

		
		// 사용자로부터 받은 relTypeCode와 relId를 기준으로 댓글을 작성
		ResultData<Integer> writeReplyRd = replyService.writeReply(rq.getLoginedMemberId(), relTypeCode, relId, body);

		int id = (int) writeReplyRd.getData1();

		// replaceUri가 비어있으면 기본값인 "../article/detail?id={relId}"로 설정
		
		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", relId);
		}

		return rq.jsReplace(writeReplyRd.getMsg(), replaceUri);
	}

	
	// 댓글삭제처리
	@RequestMapping("/usr/reply/doDelete")
	@ResponseBody
	public String doDelete(int id, String replaceUri) {

		// DB에 저장된 댓글을 가져옴
		Reply reply = replyService.getReply(id);

		
		// 댓글삭제에 대한 권한체크 및 댓글존재여부 확인
		if (reply == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 댓글은 존재하지 않습니다", id));
		}

		if (reply.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryBack("F-2", Ut.f("%d번 댓글에 대한 권한이 없습니다", id));
		}

		
		// 위 조건 충족이 되었다면 댓글삭제
		ResultData deleteReplyRd = replyService.deleteReply(id);

		if (Ut.empty(replaceUri)) {
			switch (reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}
		}

		return Ut.jsReplace(deleteReplyRd.getMsg(), replaceUri);
	}

	
	// 댓글수정
	
	@RequestMapping("/usr/reply/modify")
	public String showModify(Model model, int id, String replaceUri) {

		Reply reply = replyService.getForPrintReply(rq.getLoginedMemberId(), id);

		if (reply == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 댓글은 존재하지 않습니다!", id));
		}
		// 사용자가 해당 댓글수정이 가능한지 권한체크
		ResultData actorCanModifyRd = replyService.actorCanModify(rq.getLoginedMemberId(), reply);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBackOnView(actorCanModifyRd.getMsg());
		}

		Article article = articleService.getArticle(reply.getRelId());

		model.addAttribute("reply", reply);
		model.addAttribute("article", article);

		return "usr/reply/modify";
	}

	// 실제 댓글수정처리
	@RequestMapping("/usr/reply/doModify")
	@ResponseBody
	public String doModify(int id, String body, String replaceUri) {

		Reply reply = replyService.getReply(id);

		if (reply == null) {
			return rq.jsHistoryBack("F-1", Ut.f("%d번 댓글은 존재하지 않습니다", id));
		}

		ResultData actorCanModifyRd = replyService.actorCanModify(rq.getLoginedMemberId(), reply);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getResultCode(), actorCanModifyRd.getMsg());
		}

		ResultData modifyReplyRd = replyService.modifyReply(id, body);

		if (Ut.empty(replaceUri)) {
			switch (reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}
		}

		return rq.jsReplace(modifyReplyRd.getMsg(), replaceUri);
	}

}