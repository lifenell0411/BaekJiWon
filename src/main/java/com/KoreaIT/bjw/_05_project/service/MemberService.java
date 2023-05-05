package com.KoreaIT.bjw._05_project.service;

import org.springframework.stereotype.Service;

import com.KoreaIT.bjw._05_project.repository.MemberRepository;
import com.KoreaIT.bjw._05_project.vo.Member;

@Service
public class MemberService {
	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public int join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		return memberRepository.getLastInsertId();
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

}
