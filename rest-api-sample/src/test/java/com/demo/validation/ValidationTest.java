package com.demo.validation;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;

import com.demo.RestApiSampleApplication;
import com.demo.domain.Article;
import com.demo.validator.ProfileValidatorFactoryBean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestApiSampleApplication.class)
@WebAppConfiguration
public class ValidationTest {

	static Logger logger = Logger.getLogger(ValidationTest.class);
	
	@Autowired
	@Qualifier("ProfileValidatorFactoryBean")
	private ProfileValidatorFactoryBean validator;
	
    @Test
    public void testJsr303BeanValidator() throws ServletException, IOException {
    	
    	Article article = new Article();
    	BindException errors = new BindException(article, "contents");
    	validator.validate(article, errors);
    	List<ObjectError> allErrors = errors.getAllErrors();
    	
    	assertThat(allErrors.size()).isGreaterThan(0);
    	
    	logger.debug(allErrors);
    	
    }

}
