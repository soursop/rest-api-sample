package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.demo.domain.Article;
import com.wordnik.swagger.annotations.Api;

@Api(value = "articles", basePath="articles", description = "com.demo.repository.ArticleRepository") 
@RepositoryRestResource(collectionResourceRel = "article", path = "articles")
public interface ArticleRepository extends CrudRepository<Article, Long> {

	Article findById(@Param("id") long id);
	
	@Query("select a.id, a.writer, a.title from Article a")
	List<Article> findAllByWithOutContent();
	
}
