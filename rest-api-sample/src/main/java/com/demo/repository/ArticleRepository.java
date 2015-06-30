package com.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.demo.domain.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {

	Article findById(long id);
	
}
