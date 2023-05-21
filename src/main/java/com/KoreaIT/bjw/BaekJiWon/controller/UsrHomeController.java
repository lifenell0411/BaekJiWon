package com.KoreaIT.bjw.BaekJiWon.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.KoreaIT.bjw.BaekJiWon.vo.Rq;

@Controller
public class UsrHomeController {
	private Rq rq;

	public UsrHomeController(Rq rq) {
		this.rq = rq;
	}

	
	// main 페이지로 이동
	@RequestMapping("/usr/home/main")
	public String showMain() {
		rq.run();
		
		return "usr/home/main";

	}

	// main2 페이지로 이동
	@RequestMapping("/usr/home/main2")
	public String showMain2() {
		rq.run();
		return "usr/home/main2";
	}
	
	// localhost:8083/ 입력시 main2 페이지를 보여줌
	@RequestMapping("/")
	public String showRoot() {
		return "redirect:/usr/home/main2";
	}

}