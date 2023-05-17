package com.KoreaIT.bjw._05_project.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.KoreaIT.bjw._05_project.vo.Rq;

@Controller
public class UsrHomeController {
	private Rq rq;

	public UsrHomeController(Rq rq) {
		this.rq = rq;
	}

	@RequestMapping("/usr/home/main")
	public String showMain() {
		rq.run();
		return "usr/home/main";

	}

	
	@RequestMapping("/usr/home/main2")
	public String showMain2() {
		rq.run();
		return "usr/home/main2";
	}
	@RequestMapping("/")
	public String showRoot() {
		return "redirect:/usr/home/main2";
	}

}