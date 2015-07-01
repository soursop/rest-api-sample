package com.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.domain.Article;
import com.demo.repository.ArticleRepository;

@RestController
public class BoardController {
	
	@Autowired
	ArticleRepository articleRepositry;
	
	@RequestMapping(path="/index", method = RequestMethod.GET)
	public Iterable<Article> index() {
		Article article = new Article();
		article.setContents("test content");
		article.setTitle("TITLE");
		article.setWriter("Gyoung Jin Gim");
		articleRepositry.save(article);
		return articleRepositry.findAll();
	}

	@RequestMapping(path="/board/article", method=RequestMethod.POST)
	public void addArticle(@Valid @ModelAttribute Article article) {
		articleRepositry.save(article);
	}

	@RequestMapping(path="/board/articles/{id}/article", method=RequestMethod.GET)
	public Article getArticle(@PathVariable long id, @ModelAttribute Article article) {
		return articleRepositry.findById(id);
	}

}
