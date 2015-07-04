package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.demo.validator.ProfileValidatorFactoryBean;

@SpringBootApplication
@ComponentScan("com.demo")
public class RestApiSampleApplication extends WebMvcConfigurationSupport {
	
	@Autowired
	private Environment environment;
	
	@Bean
	ProfileValidatorFactoryBean ProfileValidatorFactoryBean() {
		return new ProfileValidatorFactoryBean(environment);
	}
	
	@Override
	protected Validator getValidator() {
      Validator validator = new ProfileValidatorFactoryBean(environment);
		return validator;
	}

    public static void main(String[] args) {
        SpringApplication.run(RestApiSampleApplication.class, args);
    }
}
