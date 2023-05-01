package com.KoreaIT.bjw._05_project.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Controller
public class UsrHomeController {

	@RequestMapping("/usr/home/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		Article article1 = new Article(1, "제목1");
		Article article2 = new Article(2, "제목2");

		List<Article> articles = new ArrayList<>();
		articles.add(article1);
		articles.add(article2);

		return articles;
	}

	@RequestMapping("/usr/home/getArticle")
	@ResponseBody
	public Article getArticle() {
		Article article = new Article(1, "제목1");

		return article;
	}

	 
 
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Article {
	private int id;
	private String title;

}