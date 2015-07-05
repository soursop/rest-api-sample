package com.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.domain.Article;
import com.demo.repository.ArticleRepository;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@Api(value = "board", basePath="board", description = "com.demo.controller.BoardController") 
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	ArticleRepository articleRepositry;

	@ApiOperation(value = "articles/dummy", notes = "add dummpy post new article")
	@RequestMapping(value={"articles/dummy"}, method = RequestMethod.GET)
	public Iterable<Article> index() {
		Article article = new Article();
		article.setContents("test content");
		article.setTitle("TITLE");
		article.setWriter("Gyoung Jin Gim");
		articleRepositry.save(article);
		return articleRepositry.findAll();
	}
	
	@ApiOperation(value = "article", notes = "post new article", httpMethod="POST")
	@RequestMapping(value={"article"}, method=RequestMethod.POST)
	public void addArticle(@Valid @ModelAttribute Article article) {
		articleRepositry.save(article);
	}

	@RequestMapping(value={"articles"}, method=RequestMethod.GET)
	public List<Article> getArticles() {
		return (List<Article>) articleRepositry.findAllByWithOutContent();
	}

	@RequestMapping(value={"articles/{id}"}, method=RequestMethod.GET)
	public Article getArticle(@PathVariable long id) {
		return getArticleById(id);
	}
	
	private Article getArticleById(long id) {
		Article findOne = articleRepositry.findOne(id);
		if (findOne == null) {
			throw new ResourceNotFoundException(String.format("{%d} ariticle is not exist", id));
		}
		return findOne;
	}

	@ApiOperation(value = "articles/{id}", notes = "delete article", httpMethod="DELETE")
	@RequestMapping(value={"articles/{id}"}, method=RequestMethod.DELETE)
	public void deleteArticle(@PathVariable long id) {
		articleRepositry.delete(id);
	}

	@ApiOperation(value = "articles/{id}", notes = "uppdate while article", httpMethod="PUT")
	@RequestMapping(value={"articles/{id}"}, method=RequestMethod.PUT)
	public Article updateEntireArticle(@PathVariable long id, @Valid @ModelAttribute Article article) {
		Article finded = getArticleById(id);
		finded.update(article);
		Article saved = articleRepositry.save(finded);
		return saved;
	}

	@ApiOperation(value = "articles/{id}", notes = "uppdate parts of article", httpMethod="PATCH")
	@RequestMapping(value={"articles/{id}"}, method=RequestMethod.PATCH)
	public Article updateArticle(@PathVariable long id, @ModelAttribute Article article) {
		Article finded = getArticleById(id);
		finded.update(article);
		Article saved = articleRepositry.save(finded);
		return saved;
	}

}
