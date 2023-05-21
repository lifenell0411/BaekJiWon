package com.KoreaIT.bjw.BaekJiWon.controller;

import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.bjw.BaekJiWon.service.ArticleService;
import com.KoreaIT.bjw.BaekJiWon.service.BoardService;
import com.KoreaIT.bjw.BaekJiWon.service.MemberService;
import com.KoreaIT.bjw.BaekJiWon.util.Ut;
import com.KoreaIT.bjw.BaekJiWon.vo.Article;
import com.KoreaIT.bjw.BaekJiWon.vo.Board;
import com.KoreaIT.bjw.BaekJiWon.vo.Member;
import com.KoreaIT.bjw.BaekJiWon.vo.ResultData;
import com.KoreaIT.bjw.BaekJiWon.vo.Rq;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private Rq rq;
	@Autowired
	private ArticleService articleService;

	@Autowired
	private BoardService boardService;

	// 회원가입 join 페이지로 이동

	@RequestMapping("/usr/member/join")
	public String showJoin() {
		return "usr/member/join";
	}
 

	// 회원가입시 사용자의 이메일 중복 여부를 확인

	@RequestMapping("/usr/member/getLoginEmailDup")
	@ResponseBody
	public ResultData getLoginEmailDup(String email) {
		if (Ut.empty(email)) {
			System.out.println("이메일이 입력되지 않았습니다.");
			return ResultData.from("F-1", "이메일을 입력해주세요");
		}
		if (!Pattern.matches("^([\\w-]+(?:\\.[\\w-]+)*)@([\\w-]+\\.)*\\w[\\w-]{0,66}\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$",
				email)) {
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
	public String doJoin(String email, String loginPw, String name, String nickname, String cellphoneNum,
			  @RequestParam(defaultValue = "/") String afterLoginUri) {

		// 필수정보를 입력해야 가입 가능하도록
		if (Ut.empty(email)) {
			return rq.jsHistoryBack("F-6", "이메일을 입력해주세요");
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
		

		// 회원 가입 결과를 담고 있는 ResultData<Integer> 객체
		ResultData<Integer> joinRd = memberService.join(email, loginPw, name, nickname, cellphoneNum );

		// joinRd.isFail()을 사용하여 회원 가입 결과가 실패인지 확인
		if (joinRd.isFail()) {
			return rq.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg());
		}

		// 성공일 경우 joinRd.getData1()을 통해 회원 가입된 회원의 id를 가져옴
		// id를 사용하여 memberService.getMemberById()를 호출하여 회원 정보를 가져옴
		Member member = memberService.getMemberById(joinRd.getData1());

		// 상위 디렉토리로 이동한 후 "/member/login" 경로와 "?afterLoginUri=" 쿼리 문자열을 조합하여 로그인 페이지의
		// URI를 생성
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
	public String doLogin(String email, String loginPw,
			@RequestParam(defaultValue = "/usr/home/main") String afterLoginUri) {

		if (rq.isLogined()) {
			return Ut.jsHistoryBack("F-5", "이미 로그인 상태입니다");
		}

		// 아이디 및 비밀번호 필수입력 체크

		if (Ut.empty(email)) {
			return Ut.jsHistoryBack("F-1", "아이디를 입력해주세요");
		}
		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("F-2", "비밀번호를 입력해주세요");
		}

		// 입력한 loginId와 패스워드를 DB에서 가져와 비교

		Member member = memberService.getMemberByLoginId(email);

		if (member == null) {
			return Ut.jsHistoryBack("F-3", Ut.f("%s는 존재하지 않는 아이디입니다", email));
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("F-4", Ut.f("비밀번호가 일치하지 않습니다"));
		}
		if (member.isDelStatus() == true) {
			return Ut.jsReplace("사용정지된 계정입니다", "/");
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
	public String showMyPage(Model model,@RequestParam(defaultValue = "1") int boardId,
			@RequestParam(defaultValue = "title,body") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword, @RequestParam(defaultValue = "1") int page) {
		Article article = articleService.getForLikePointArticle(rq.getLoginedMemberId());
	 
		Board board = boardService.getBoardById(boardId);
		// 게시판이 존재하지 않을 경우 뒤로가기
		if (board == null) {
			return rq.jsHistoryBackOnView("없는 게시판입니다.");
		}
		 
		 
		model.addAttribute("board", board);
		model.addAttribute("boardId", boardId);
	 
		model.addAttribute("article", article);
		return "usr/member/myPage";
	}

	// 회원정보 수정시 한번더 비밀번호를 체크할 수 있도록 checkPw 페이지로 이동

 
	 
	// 회원정보 수정 페이지로 이동

	@RequestMapping("/usr/member/modify")
	public String showModify() {

		return "usr/member/modify";
	}

	// 회원정보 수정
	// 필수정보가 빈칸이면 수정되지 않도록 제어

	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public String doModify(String email, String loginPw, String name, String nickname, String cellphoneNum 
			 ) {

		Member member = memberService.getMemberByLoginId(email);
	 
		if (Ut.empty(loginPw)) {
			loginPw = member.getLoginPw();
		} else {
			loginPw = Ut.sha256(loginPw);
		}
		if (Ut.empty(name)) {
			return rq.jsHistoryBackOnView("name 입력해");
		}
		if (Ut.empty(nickname)) {
			return rq.jsHistoryBackOnView("nickname 입력해");
		}
		if (Ut.empty(cellphoneNum)) {
			return rq.jsHistoryBackOnView("cellphoneNum 입력해");
		}
		 

		ResultData modifyRd = memberService.modify(rq.getLoginedMemberId(), loginPw, name, nickname, cellphoneNum
			);

		return rq.jsReplace(modifyRd.getMsg(), "../member/myPage");
	}

}