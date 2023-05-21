package com.KoreaIT.bjw.BaekJiWon.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.KoreaIT.bjw.BaekJiWon.service.ArticleService;
import com.KoreaIT.bjw.BaekJiWon.service.BoardService;
import com.KoreaIT.bjw.BaekJiWon.service.GenFileService;
import com.KoreaIT.bjw.BaekJiWon.service.LikePointService;
import com.KoreaIT.bjw.BaekJiWon.service.ReactionPointService;
import com.KoreaIT.bjw.BaekJiWon.service.ReplyService;
import com.KoreaIT.bjw.BaekJiWon.util.Ut;
import com.KoreaIT.bjw.BaekJiWon.vo.Article;
import com.KoreaIT.bjw.BaekJiWon.vo.Board;
import com.KoreaIT.bjw.BaekJiWon.vo.Reply;
import com.KoreaIT.bjw.BaekJiWon.vo.ResultData;
import com.KoreaIT.bjw.BaekJiWon.vo.Rq;

@Controller
public class UsrArticleController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private LikePointService likePointService;
	@Autowired
	private Rq rq;
	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private GenFileService genFileService;

	// 게시판 ID를 기반으로 게시물 목록을 가져와서 사용자 페이지에서 리스트로 표시하는 기능
	// 페이지네이션 및 검색 기능을 제공, 사용자가 원하는 데이터를 찾을 수 있도록 함.
	// 만약 요청된 게시판 ID에 해당하는 게시판이 존재하지 않을 경우, 이전 페이지로 돌아가는 기능

	@RequestMapping("/usr/article/list")
	public String showList(Model model, @RequestParam(defaultValue = "1") int boardId,
			@RequestParam(defaultValue = "title,body") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword, @RequestParam(defaultValue = "1") int page) {

		Board board = boardService.getBoardById(boardId);
		// 게시판이 존재하지 않을 경우 뒤로가기
		if (board == null) {
			return rq.jsHistoryBackOnView("없는 게시판입니다.");
		}

		// 페이지네이션 기능
		// 현재 게시글의 총 갯수를 가져옴
		int articlesCount = articleService.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);
		// 한페이지당 최대 10개의 게시글을 보여줌
		int itemsInAPage = 10;
		// 전체 페이지 수를 계산하여 저장
		int pagesCount = (int) Math.ceil(articlesCount / (double) itemsInAPage);

		
 
		List<Article> articles = articleService.getForPrintArticles(boardId, itemsInAPage, page, searchKeywordTypeCode,
				searchKeyword);

	
		model.addAttribute("searchKeywordTypeCode", searchKeywordTypeCode);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("board", board);
		model.addAttribute("boardId", boardId);
		model.addAttribute("page", page);
		model.addAttribute("pagesCount", pagesCount);
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("articles", articles);

		return "usr/article/list";
	}

	// 특정 ID의 게시물을 수정하기 위한 수정 페이지를 표시하는 기능
	
	@RequestMapping("/usr/article/modify")
	public String showModify(Model model, int id) {

		
		// 요청된 게시물을 가져와서 존재 여부를 확인, 권한 검사
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
 
		if (article == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 글은 존재하지 않습니다!", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBackOnView(actorCanModifyRd.getMsg());
		}

		model.addAttribute("article", article);
		 
		return "usr/article/modify";
	}

	// 특정 ID의 게시물을 수정

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {

		Article article = articleService.getArticle(id);

	 
		if (article == null) {
			return rq.jsHistoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다@", id));
		}

		// 게시글을 수정할 수 있는 권한이 있는지 체크
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getResultCode(), actorCanModifyRd.getMsg());
		}

		articleService.modifyArticle(id, title, body);
 
		return rq.jsReplace(Ut.f("%d번 글을 수정 했습니다", id), Ut.f("../article/detail?id=%d", id));
	}

	// 특정 id에 대한 게시글 삭제
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {

		Article article = articleService.getArticle(id);
		if (article == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다", id));
		}

		// 게시글 삭제에 대한 권한여부 체크
		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryBack("F-2", Ut.f("%d번 글에 대한 권한이 없습니다", id));
		}
		// 권한이 있다면 삭제
		articleService.deleteArticle(id);

		return Ut.jsReplace(Ut.f("%d번 글을 삭제 했습니다", id), "../article/list?boardId=1");
	}

	// 게시글 작성 jsp페이지로 이동
	@RequestMapping("/usr/article/write")
	public String showWrite(String title, String body) {

		return "usr/article/write";
	}

	// 실제 게시글 작성 처리

	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(int boardId, String title, String body, String replaceUri,
			MultipartRequest multipartRequest) {

		// 제목 및 내용 입력하지 않을 시 게시글 작성 불가
		if (Ut.empty(title)) {
			return rq.jsHistoryBack("F-1", "제목을 입력해주세요");
		}
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("F-2", "내용을 입력해주세요");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), boardId, title, body);

		int id = (int) writeArticleRd.getData1();

		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", id);
		}

		// 첨부파일 업로드기능

		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		for (String fileInputName : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(fileInputName);

			if (multipartFile.isEmpty() == false) {
				genFileService.save(multipartFile, id);
			}
		}

		return rq.jsReplace(Ut.f("%d번 글이 생성되었습니다", id), replaceUri);
	}

	// 게시글 상세페이지 보기
	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		// 좋아요 싫어요 할 수 있는지 권한체크
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				"article", id);

		ResultData actorCanMakeLikeRd = likePointService.actorCanMakeLike(rq.getLoginedMemberId(), "article", id);

		// 댓글 불러오기
		List<Reply> replies = replyService.getForPrintReplies(rq.getLoginedMemberId(), "article", id);

		int repliesCount = replies.size();

		if (actorCanMakeReactionRd.isSuccess()) {
			model.addAttribute("actorCanMakeReaction", actorCanMakeReactionRd.isSuccess());

		}
		if (actorCanMakeLikeRd.isSuccess()) {
			model.addAttribute("actorCanMakeLike", actorCanMakeLikeRd.isSuccess());

		}

	
		if (actorCanMakeReactionRd.getResultCode().equals("F-2")) {
			int sumReactionPointByMemberId = (int) actorCanMakeReactionRd.getData1();

			if (sumReactionPointByMemberId > 0) {
				model.addAttribute("actorCanCancelGoodReaction", true);
			} else {
				model.addAttribute("actorCanCancelBadReaction", true);
			}
		}

		if (actorCanMakeLikeRd.getResultCode().equals("F-2")) {
			int sumLikePointByMemberId = (int) actorCanMakeLikeRd.getData1();

			if (sumLikePointByMemberId > 0) {
				model.addAttribute("actorCanCancelLike", true);

			}
		}
		
		model.addAttribute("repliesCount", repliesCount);
		model.addAttribute("replies", replies);
		model.addAttribute("article", article);
		model.addAttribute("isAlreadyAddGoodRp", reactionPointService.isAlreadyAddGoodRp(id, "article"));
		model.addAttribute("isAlreadyAddBadRp", reactionPointService.isAlreadyAddBadRp(id, "article"));
		model.addAttribute("isAlreadyAddLikeRp", likePointService.isAlreadyAddLikeRp(id, "article"));


		return "usr/article/detail";
	}

	
	// 조회수 증가
	
	@RequestMapping("/usr/article/doIncreaseHitCountRd")
	@ResponseBody
	public ResultData doIncreaseHitCountRd(int id) {

		ResultData increaseHitCountRd = articleService.increaseHitCount(id);

		if (increaseHitCountRd.isFail()) {
			return increaseHitCountRd;
		}

		ResultData rd = ResultData.newData(increaseHitCountRd, "hitCount", articleService.getArticleHitCount(id));

		rd.setData2("id", id);

		return rd;
	}

	
	// about 페이지로 이동
	@RequestMapping("/usr/article/about")
	public String showAbout() {

		return "usr/article/about";
	}

	
	 
}