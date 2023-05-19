package com.KoreaIT.bjw._05_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.bjw._05_project.repository.ArticleRepository;
import com.KoreaIT.bjw._05_project.util.Ut;
import com.KoreaIT.bjw._05_project.vo.Article;
import com.KoreaIT.bjw._05_project.vo.ResultData;
@Service
public class ArticleService {

	
	@Autowired
	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	
	// 게시글 작성관련 메서드
	// 게시글을 작성할 당시의 memberId와 boardId, 제목과 내용을 인자로 받아 게시글을 작성
	public ResultData<Integer> writeArticle(int memberId, int boardId, String title, String body) {

		articleRepository.writeArticle(memberId, boardId, title, body);

		// 방금 생성된 게시글의 식별자(ID)를 가져옴
		int id = articleRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 글이 생성되었습니다", id), "id", id);

	}

	// 게시글 삭제 메서드
	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	// 게시글 수정 메서드
	// id, title, body를 인자로 받아 id에 해당하는 게시글을 찾아서 제목과 내용을 수정
	public ResultData modifyArticle(int id, String title, String body) {

		articleRepository.modifyArticle(id, title, body);

		Article article = getArticle(id);

		return ResultData.from("S-1", Ut.f("%d번 글을 수정 했습니다", id), "article", article);
	}

	// DB에서 모든 게시글 데이터를 가져오는 메서드
	public List<Article> articles() {
		return articleRepository.getArticles();
	}

	
	// DB에서 id를 인자로 넘겨주어 id와 일치하는 게시글만 가져오는 메서드
	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}

	// 특정 id에 해당하는 게시글의 정보만 가져오는 메서드
	public Article getForPrintArticle(int actorId, int id) {
		Article article = articleRepository.getForPrintArticle(id);

		controlForPrintData(actorId, article);

		return article;
	}

	// 특정 사용자(actor)의 게시글에 대한 권한 여부를 판단
	private void controlForPrintData(int actorId, Article article) {
		if (article == null) {
			return;
		}

		ResultData actorCanDeleteRd = actorCanDelete(actorId, article);
		article.setActorCanDelete(actorCanDeleteRd.isSuccess());

		ResultData actorCanModifyRd = actorCanModify(actorId, article);
		article.setActorCanModify(actorCanModifyRd.isSuccess());
	}

	
	// 로그인한 사용자가 해당 게시글을 수정할 수 있는지 권한여부 체크
	public ResultData actorCanModify(int loginedMemberId, Article article) {
		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("해당 글에 대한 권한이 없습니다"));
		}
		return ResultData.from("S-1", "수정 가능");
	}

	
	// 로그인한 사용자가 해당 게시물을 삭제할 수 있는지 권한여부 체크
	private ResultData actorCanDelete(int actorId, Article article) {
		if (article == null) {
			return ResultData.from("F-1", "게시물이 존재하지 않습니다");
		}

		if (article.getMemberId() != actorId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다");
		}

		return ResultData.from("S-1", "삭제 가능");
	}

	
	// 페이지네이션, 특정 게시판에서 페이지별로 출력할 게시글 목록을 조회
	
	public List<Article> getForPrintArticles(int boardId, int itemsInAPage, int page, String searchKeywordTypeCode,
			String searchKeyword) {

		// 조회할 게시글의 시작 인덱스를 계산
		int limitFrom = (page - 1) * itemsInAPage;
		
		int limitTake = itemsInAPage;

		return articleRepository.getForPrintArticles(boardId, searchKeywordTypeCode, searchKeyword, limitFrom,
				limitTake);
	}

	// 현재 게시글의 총 갯수
	// searchKeyword가 비어있지 않은 경우, 검색 키워드에 따라 게시글을 필터링
	// searchKeywordTypeCode에 따라 검색 키워드가 제목(title) 또는 내용(body)과 일치하는 경우의 게시글을 가져옴
	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
		return articleRepository.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);
	}

	
	// 조회수 증가 
	public ResultData increaseHitCount(int id) {
		int affectedRow = articleRepository.increaseHitCount(id);

		
		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없음", "affectedRow", affectedRow);
		}

		return ResultData.from("S-1", "조회수 증가", "affectedRowRd", affectedRow);
	}

	// 특정 게시글의 조회수를 가져옴
	public int getArticleHitCount(int id) {
		return articleRepository.getArticleHitCount(id);
	}

	
	// 좋아요 클릭시 좋아요를 증가, 게시글의 id를 인자로 넘겨줌
	public ResultData increaseGoodReationPoint(int relId) {
		int affectedRow = articleRepository.increaseGoodReationPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 증가", "affectedRow", affectedRow);
	}

	
	// 싫어요 클릭시 싫어요를 증가, 게시글의 id를 인자로 넘겨줌
	public ResultData increaseBadReationPoint(int relId) {

		int affectedRow = articleRepository.increaseBadReationPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 증가", "affectedRow", affectedRow);
	}

	
	// 좋아요를 한 후 다시 좋아요 클릭시 좋아요를 감소, 게시글의 id를 인자로 넘겨줌
	public ResultData decreaseGoodReationPoint(int relId) {
		int affectedRow = articleRepository.decreaseGoodReationPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "좋아요 감소", "affectedRow", affectedRow);
	}

	
	// 싫어요를 한 후 다시 좋아요 클릭시 싫어요를 감소, 게시글의 id를 인자로 넘겨줌
	public ResultData decreaseBadReationPoint(int relId) {
		int affectedRow = articleRepository.decreaseBadReationPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "싫어요 감소", "affectedRow", affectedRow);

	}

	
 
	
	// 찜하기 클릭시 likePoint 증가, 게시글의 id를 인자로 넘겨줌
	public ResultData increaseLikePoint(int relId) {
		int affectedRow = articleRepository.increaseLikePoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "찜하기 증가", "affectedRow", affectedRow);
	}
	
	// 찜하기 된 상태에서 찜하기를 다시 클릭시 likePoint 감소, 게시글의 id를 인자로 넘겨줌
	public ResultData decreaseLikePoint(int relId) {
		int affectedRow = articleRepository.decreaseLikePoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물은 없습니다", "affectedRow", affectedRow);
		}
		return ResultData.from("S-1", "찜하기 감소", "affectedRow", affectedRow);
	}
	
	
}