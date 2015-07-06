package com.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.demo.repository.SelectRepositoryFactoryBean;
import com.demo.validator.ProfileValidatorFactoryBean;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * RestApiSampleApplication
 * @author Gim Gyoung Jin
 *
 */
@SpringBootApplication
@ComponentScan("com.demo")
@EnableJpaRepositories(repositoryFactoryBeanClass = SelectRepositoryFactoryBean.class)
public class RestApiSampleApplication extends WebMvcConfigurationSupport {

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
		"classpath:/META-INF/resources/", "classpath:/resources/",
		"classpath:/static/", "classpath:/public/" };
	
	@Autowired
	private Environment environment;

	private ApplicationContext applicationContext;
	
	@Bean
	ProfileValidatorFactoryBean ProfileValidatorFactoryBean() {
		return new ProfileValidatorFactoryBean(environment);
	}
	
	@Override
	protected Validator getValidator() {
      Validator validator = new ProfileValidatorFactoryBean(environment);
		return validator;
	}
	
	/**
	 * Custom Object Mapper Builder for Object format configuration
	 * @return Jackson2ObjectMapperBuilder
	 */
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
	    Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
	    builder.failOnEmptyBeans(false);
	    builder.serializationInclusion(JsonInclude.Include.NON_NULL);
	    return builder;
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return objectMapperBuilder().build();
	}
	
    /**
     * Override Message Cononverter 
     * to replace Default JacksonMapper to Custom JacksonMapper 
     */
	@Override
	protected void extendMessageConverters(
			List<HttpMessageConverter<?>> converters) {
    	for (int i=0; i< converters.size(); i++) {
    		HttpMessageConverter<?> httpMessageConverter = converters.get(i);
    		if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
    			converters.remove(i);
    		}
    	}
		ObjectMapper objectMapper = objectMapperBuilder().applicationContext(getApplicationContext()).build();
		converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
    }
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		super.setApplicationContext(applicationContext);
		this.applicationContext = applicationContext;
	}

    private ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!registry.hasMappingForPattern("/webjars/**")) {
			registry.addResourceHandler("/webjars/**").addResourceLocations(
					"classpath:/META-INF/resources/webjars/");
		}
		if (!registry.hasMappingForPattern("/**")) {
			registry.addResourceHandler("/**").addResourceLocations(
					CLASSPATH_RESOURCE_LOCATIONS);
		}
	}
    
	public static void main(String[] args) {
        SpringApplication.run(RestApiSampleApplication.class, args);
    }
}
