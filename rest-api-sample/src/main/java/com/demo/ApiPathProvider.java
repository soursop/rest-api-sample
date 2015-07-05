package com.demo;

import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;

import com.mangofactory.swagger.paths.SwaggerPathProvider;

public class ApiPathProvider extends SwaggerPathProvider {
    public static final List<String> DEFAULT_INCLUDE_PATTERNS = Arrays.asList("/board/.*");
    private SwaggerPathProvider defaultSwaggerPathProvider;
    @Autowired
    private ServletContext servletContext;
 
    private String docsLocation;
 
    public ApiPathProvider(String docsLocation) {
        this.docsLocation = docsLocation;
    }
 
    @Override
    public String getApiResourcePrefix() {
        return defaultSwaggerPathProvider.getApiResourcePrefix();
    }
 
    public String getAppBasePath() {
        return UriComponentsBuilder
                .fromHttpUrl(docsLocation)
                .path(servletContext.getContextPath())
                .build()
                .toString();
    }
 
    public void setDefaultSwaggerPathProvider(SwaggerPathProvider defaultSwaggerPathProvider) {
        this.defaultSwaggerPathProvider = defaultSwaggerPathProvider;
    }
 
	@Override
	protected String applicationPath() {
		return getAppBasePath();
	}

	@Override
	protected String getDocumentationPath() {
        return UriComponentsBuilder
                .fromHttpUrl(getAppBasePath())
                .pathSegment("api-docs/")
                .build()
                .toString();
	}
}
