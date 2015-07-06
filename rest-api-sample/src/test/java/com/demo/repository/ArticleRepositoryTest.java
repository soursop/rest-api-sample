package com.demo.repository;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.demo.RestApiSampleApplication;
import com.demo.domain.Article;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestApiSampleApplication.class)
@WebAppConfiguration
public class ArticleRepositoryTest {

	static Logger logger = Logger.getLogger(ArticleRepositoryTest.class);
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private EntityManager em;
	
	@Before
	public void before() {
		Article article = new Article();
		article.setTitle("TITLE");
		article.setWriter("Janny");
		article.setContents("test contents");
		articleRepository.save(article);
	}

	@Test
	public void testCustomRepository() {
		
		List<Article> findAllByWithoutContents = articleRepository.findAllBySelectInputs(Arrays.asList("id", "title", "writer"));
		assertThat(findAllByWithoutContents.size()).isGreaterThan(0);
		assertThat(findAllByWithoutContents.get(0).getContents()).isNull();
		
	}
	
	@Test
	public void testSelectByCriteria() throws Exception {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Article> query = builder.createQuery(Article.class);
		Root<Article> c = query.from(Article.class);
		List<String> stringsOfInputs = Arrays.asList("id", "title", "writer");
		List<Selection<?>> inputs = new ArrayList<Selection<?>>();
		for (String select : stringsOfInputs) {
			inputs.add(c.get(select));
		}
		query.multiselect(inputs);
		List<Article> results = em.createQuery(query).getResultList();
		for (Article article : results) {
			assertThat(article.getContents()).isNull();
		}
		logger.debug(results);
		
	}

}
