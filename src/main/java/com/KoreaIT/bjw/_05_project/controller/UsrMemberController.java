package com.KoreaIT.bjw._05_project.controller;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.bjw._05_project.service.MemberService;
import com.KoreaIT.bjw._05_project.util.Ut;
import com.KoreaIT.bjw._05_project.vo.Member;
import com.KoreaIT.bjw._05_project.vo.ResultData;
import com.KoreaIT.bjw._05_project.vo.Rq;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private Rq rq;

	
	// 회원가입 join 페이지로 이동
	
	@RequestMapping("/usr/member/join")
	public String showJoin() {
		return "usr/member/join";
	}
	
	// 회원가입시 사용자의 로그인 아이디(loginId)의 중복 여부를 확인
	
	@RequestMapping("/usr/member/getLoginIdDup")
	@ResponseBody
	public ResultData getLoginIdDup(String loginId) {

		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요");
		}
		// DB에 저장되어있는 loginId를 가져와 중복여부 체크
		Member existsMember = memberService.getMemberByLoginId(loginId);

		if (existsMember != null) {
			return ResultData.from("F-2", "해당 아이디는 이미 사용중입니다.", "loginId", loginId);
		}

		return ResultData.from("S-1", "사용 가능한 아이디입니다.", "loginId", loginId);
	}

	
	// 회원가입시 사용자의 이메일 중복 여부를 확인
	
	@RequestMapping("/usr/member/getLoginEmailDup")
	@ResponseBody
	public ResultData getLoginEmailDup(String email) {
		if (Ut.empty(email)) {
		    System.out.println("이메일이 입력되지 않았습니다.");
		    return ResultData.from("F-1", "이메일을 입력해주세요");
		}
	    if (!Pattern.matches("^([\\w-]+(?:\\.[\\w-]+)*)@([\\w-]+\\.)*\\w[\\w-]{0,66}\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$", email)) {
	        return ResultData.from("F-2", "이메일 형식이 올바르지 않습니다.", "email", email);
	    }
	    
	    // DB에 저장되어있는 이메일정보를 가져와 중복여부 체크
	    
	    Member existsMember = memberService.getMemberByEmail(email);
	    if (existsMember != null) {
	        return ResultData.from("F-3", "해당 이메일은 이미 사용중입니다.", "email", email);
	    }

	    return ResultData.from("S-1", "사용 가능한 이메일입니다.", "email", email);
	}


	// 실제 회원가입 처리
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email, @RequestParam(defaultValue = "/") String afterLoginUri) {

		// 필수정보를 입력해야 가입 가능하도록
		
		if (Ut.empty(loginId)) {
			return rq.jsHistoryBack("F-1", "아이디를 입력해주세요");
		}
		if (Ut.empty(loginPw)) {
			return rq.jsHistoryBack("F-2", "비밀번호를 입력해주세요");
		}
		if (Ut.empty(name)) {
			return rq.jsHistoryBack("F-3", "이름을 입력해주세요");
		}
		if (Ut.empty(nickname)) {
			return rq.jsHistoryBack("F-4", "닉네임을 입력해주세요");
		}
		if (Ut.empty(cellphoneNum)) {
			return rq.jsHistoryBack("F-5", "전화번호를 입력해주세요");
		}
		if (Ut.empty(email)) {
			return rq.jsHistoryBack("F-6", "이메일을 입력해주세요");
		}

		// 회원 가입 결과를 담고 있는 ResultData<Integer> 객체
		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		
		// joinRd.isFail()을 사용하여 회원 가입 결과가 실패인지 확인
		if (joinRd.isFail()) {
			return rq.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg());
		}

		
		// 성공일 경우 joinRd.getData1()을 통해 회원 가입된 회원의 id를 가져옴
		// id를 사용하여 memberService.getMemberById()를 호출하여 회원 정보를 가져옴
		Member member = memberService.getMemberById(joinRd.getData1());

		
		// 상위 디렉토리로 이동한 후 "/member/login" 경로와 "?afterLoginUri=" 쿼리 문자열을 조합하여 로그인 페이지의 URI를 생성
		String afterJoinUri = "../member/login?afterLoginUri=" + Ut.getEncodedUri(afterLoginUri);

		return Ut.jsReplace("S-1", Ut.f("회원가입이 완료되었습니다"), afterJoinUri);
	}

	
	// 로그인페이지 이동
	
	@RequestMapping("/usr/member/login")
	public String showLogin(HttpSession httpSession) {
		return "usr/member/login";
	}

	// 실제 로그인 페이지, 이미 로그인 되어있다면 로그인상태라고 알려줌
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw, @RequestParam(defaultValue = "/usr/home/main") String afterLoginUri) {

		if (rq.isLogined()) {
			return Ut.jsHistoryBack("F-5", "이미 로그인 상태입니다");
		}

		// 아이디 및 비밀번호 필수입력 체크
		
		if (Ut.empty(loginId)) {
			return Ut.jsHistoryBack("F-1", "아이디를 입력해주세요");
		}
		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("F-2", "비밀번호를 입력해주세요");
		}

		// 입력한 loginId와 패스워드를 DB에서 가져와 비교 
		
		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("F-3", Ut.f("%s는 존재하지 않는 아이디입니다", loginId));
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("F-4", Ut.f("비밀번호가 일치하지 않습니다"));
		}

		rq.login(member);

		// 우리가 갈 수 있는 경로를 경우의 수로 표현
		// 인코딩
		// 그 외에는 처리 불가 -> 메인으로 보내자

		return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getName()), afterLoginUri);
	}

	
	// 로그아웃 페이지
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout(@RequestParam(defaultValue = "/usr/home/main") String afterLogoutUri) {

		rq.logout();

		return Ut.jsReplace("S-1", "로그아웃 되었습니다", afterLogoutUri);
	}

	
	// myPage로 이동
	
	@RequestMapping("/usr/member/myPage")
	public String showMyPage() {

		return "usr/member/myPage";
	}

	
	// 회원정보 수정시 한번더 비밀번호를 체크할 수 있도록 checkPw 페이지로 이동
	
	@RequestMapping("/usr/member/checkPw")
	public String showCheckPw() {

		return "usr/member/checkPw";
	}

	// checkPw 기능
 
	@RequestMapping("/usr/member/doCheckPw")
	@ResponseBody
	public String doCheckPw(String loginPw, String replaceUri) {
	 
		if (Ut.empty(loginPw)) {
			return rq.jsHistoryBackOnView("비밀번호를 입력해주세요");
		}

		if (rq.getLoginedMember().getLoginPw().equals(loginPw) == false) {
			return rq.jsHistoryBack("", "비밀번호가 틀립니다");
		}

		return rq.jsReplace("", replaceUri);
	}

	
	// 회원정보 수정 페이지로 이동
	
	@RequestMapping("/usr/member/modify")
	public String showModify() {

		return "usr/member/modify";
	}

	
	// 회원정보 수정
	// 필수정보가 빈칸이면 수정되지 않도록 제어
	
	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public String doModify(String loginPw, String name, String nickname, String cellphoneNum, String email) {

		if (Ut.empty(loginPw)) {
			loginPw = null;
		}
		if (Ut.empty(name)) {
			return rq.jsHistoryBackOnView("name을 입력해주세요");
		}
		if (Ut.empty(nickname)) {
			return rq.jsHistoryBackOnView("nickname을 입력해주세요");
		}
		if (Ut.empty(cellphoneNum)) {
			return rq.jsHistoryBackOnView("cellphoneNum을 입력해주세요");
		}
		if (Ut.empty(email)) {
			return rq.jsHistoryBackOnView("email을 입력해주세요");
		}

		ResultData modifyRd = memberService.modify(rq.getLoginedMemberId(), loginPw, name, nickname, cellphoneNum,
				email);

		return rq.jsReplace(modifyRd.getMsg(), "../member/myPage");
	}

}