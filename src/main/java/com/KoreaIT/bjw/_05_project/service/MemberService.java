package com.KoreaIT.bjw._05_project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.KoreaIT.bjw._05_project.repository.MemberRepository;
import com.KoreaIT.bjw._05_project.util.Ut;
import com.KoreaIT.bjw._05_project.vo.Member;
import com.KoreaIT.bjw._05_project.vo.ResultData;


@Service
public class MemberService {
	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	
	// 사용자가 지정한 조건에 따라 회원의 수를 조회
	public int getMembersCount(String authLevel, String searchKeywordTypeCode, String searchKeyword) {
		return memberRepository.getMembersCount(authLevel, searchKeywordTypeCode, searchKeyword);
	}

	
	 // 사용자가 지정한 따라 List에 회원데이터를 담아서 보여줌
	public List<Member> getForPrintMembers(String authLevel, String searchKeywordTypeCode, String searchKeyword,
			int itemsInAPage, int page) {

		int limitStart = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;
		List<Member> members = memberRepository.getForPrintMembers(authLevel, searchKeywordTypeCode, searchKeyword,
				limitStart, limitTake);

		return members;
	}

	// 회원가입 메서드
	// 아이디, 패스워드, 이름, 닉네임, 휴대폰, 이메일정보를 인자로 넘겨받음
	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		// 로그인 아이디 중복체크
		Member existsMember = getMemberByLoginId(loginId);

		if (existsMember != null) {
			return ResultData.from("F-7", Ut.f("이미 사용중인 아이디(%s)입니다", loginId));
		}

		// 이름 + 이메일 중복체크
		existsMember = getMemberByNameAndEmail(name, email);

		if (existsMember != null) {
			return ResultData.from("F-8", Ut.f("이미 사용중인 이름(%s)과 이메일(%s)입니다", name, email));
		}

		// 중복체크 후 회원가입처리
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		int id = memberRepository.getLastInsertId();

		return ResultData.from("S-1", "회원가입이 완료되었습니다", "id", id);
	}

	
	// 이름과 이메일을 인자로 넘겨주면서 DB에 이미 있는 이메일과 이름인지 중복체크
	public Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	// 로그인아이디를 인자로 넘겨주면서 DB에 이미 있는 로그인아이디인지 중복체크
	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	
	// id를 인자로 받아 회원 정보를 확인
	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

	
	// 회원정보 수정
	// id, loginPw, name, nickname, cellphoneNum, email 을 인자로 전달받음
	
	public ResultData modify(int id, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		memberRepository.modify(id,loginPw, name, nickname, cellphoneNum, email);
		return ResultData.from("S-1", "회원 정보 수정이 완료되었습니다");
	}

	// 회원이 입력한 email을 인자로 전달받아 이미 가입된 이메일인지 확인
	public Member getMemberByEmail(String email) {
		return memberRepository.getMemberByEmail(email);
	}
	public void deleteMembers(List<Integer> memberIds) {
		for (int memberId : memberIds) {
			Member member = getMemberById(memberId);

			if (member != null) {
				deleteMember(member);
			}
		}
	}

	private void deleteMember(Member member) {
		memberRepository.deleteMember(member.getId());
	}


}