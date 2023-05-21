package com.KoreaIT.bjw.BaekJiWon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.bjw.BaekJiWon.repository.ReplyRepository;
import com.KoreaIT.bjw.BaekJiWon.util.Ut;
import com.KoreaIT.bjw.BaekJiWon.vo.Reply;
import com.KoreaIT.bjw.BaekJiWon.vo.ResultData;

@Service
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepository;

	public ReplyService(ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}

	// 댓글작성 메서드
	// 사용자의 id, 게시판타입, 게시글번호, 댓글내용을 받아 댓글DB에 insert
	public ResultData<Integer> writeReply(int actorId, String relTypeCode, int relId, String body) {
		replyRepository.writeReply(actorId, relTypeCode, relId, body);

		int id = replyRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 댓글이 생성되었습니다", id), "id", id);
	}

	// 게시판 타입과 게시글 번호에 따라 남겨진 댓글을 불러와 보여줌
	public List<Reply> getForPrintReplies(int actorId, String relTypeCode, int relId) {
		List<Reply> replies = replyRepository.getForPrintReplies(actorId, relTypeCode, relId);

		// 가져온 각 댓글에 대해 출력용 데이터를 제어
		for (Reply reply : replies) {
			controlForPrintData(actorId, reply);
		}

		return replies;
	}

	private void controlForPrintData(int actorId, Reply reply) {

		if (reply == null) {
			return;
		}
		// 해당 사용자(actorId)가 댓글을 삭제할 수 있는지 확인하여 결과를 설정
		ResultData actorCanDeleteRd = actorCanDelete(actorId, reply);
		reply.setActorCanDelete(actorCanDeleteRd.isSuccess());

		// 해당 사용자(actorId)가 댓글을 수정할 수 있는지 확인하여 결과를 설정
		ResultData actorCanModifyRd = actorCanModify(actorId, reply);
		reply.setActorCanModify(actorCanModifyRd.isSuccess());
	}

	
	// 로그인한 사용자가 특정 댓글에 대해 수정 가능한지 권한여부 체크 및 수정처리
	public ResultData actorCanModify(int loginedMemberId, Reply reply) {
		if (reply.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("해당 댓글에 대한 권한이 없습니다"));
		}
		return ResultData.from("S-1", "수정 가능");
	}

	// 로그인한 사용자가 특정 댓글에 대해 삭제 가능한지 권한여부 체크 및 삭제처리
	private ResultData actorCanDelete(int actorId, Reply reply) {
		if (reply == null) {
			return ResultData.from("F-1", "댓글이 존재하지 않습니다");
		}

		// memberId와 인자로 받안 actorId를 비교하여 권한체크
		if (reply.getMemberId() != actorId) {
			return ResultData.from("F-2", "해당 댓글에 대한 권한이 없습니다");
		}

		return ResultData.from("S-1", "삭제 가능");
	}

	
	// 인자로 받은 id와 일치하는 댓글을 가져옴
	public Reply getReply(int id) {
		return replyRepository.getReply(id);
	}

	// 특정 id를 가진 댓글을 DB에서 삭제
	public ResultData deleteReply(int id) {
		replyRepository.deleteReply(id);
		return ResultData.from("S-1", Ut.f("%d번 댓글을 삭제했습니다", id));
	}

	
	// 특정 회원의 아이디와 댓글 id를 받아, 해당하는 댓글을 가져옴
	public Reply getForPrintReply(int actorId, int id) {
		Reply reply = replyRepository.getForPrintReply(id);

		controlForPrintData(actorId, reply);
		return reply;
	}

	
	// 특정 댓글 정보를 가져와 댓글내용 수정
	public ResultData modifyReply(int id, String body) {
		replyRepository.modifyReply(id, body);

		return ResultData.from("S-1", Ut.f("%d번 댓글을 수정했습니다", id));
	}

}