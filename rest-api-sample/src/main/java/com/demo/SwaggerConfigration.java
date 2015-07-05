package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
@EnableSwagger
@ComponentScan("com.demo")
public class SwaggerConfigration implements EnvironmentAware {
    public static final String DEFAULT_INCLUDE_PATTERN = "/board/.*";
//    public static final String DEFAULT_INCLUDE_PATTERN = "/(board|\\{repository\\})/.*";
 
    private RelaxedPropertyResolver propertyResolver;
    @Value("${app.docs}")
    private String docsLocation;
    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;
    
    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "swagger.");
    }
 
    /**
     * Swagger Spring MVC configuration
     */
    @Bean
    public SwaggerSpringMvcPlugin swaggerSpringMvcPlugin(SpringSwaggerConfig springSwaggerConfig) {
        return new SwaggerSpringMvcPlugin(springSwaggerConfig)
                .apiInfo(apiInfo())
                .genericModelSubstitutes(ResponseEntity.class)
                .pathProvider(apiPathProvider())
                .includePatterns(DEFAULT_INCLUDE_PATTERN)
                .build();
    }
    /**
     * Example of a custom path provider
     */
    @Bean
    public ApiPathProvider apiPathProvider() {
        ApiPathProvider apiPathProvider = new ApiPathProvider(docsLocation);
        apiPathProvider.setDefaultSwaggerPathProvider(springSwaggerConfig.defaultSwaggerPathProvider());
        return apiPathProvider;
    }
 
    /**
     * API Info as it appears on the swagger-ui page
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                propertyResolver.getProperty("title"),
                propertyResolver.getProperty("description"),
                propertyResolver.getProperty("termsOfServiceUrl"),
                propertyResolver.getProperty("contact"),
                propertyResolver.getProperty("license"),
                propertyResolver.getProperty("licenseUrl"));
    }
    
}
