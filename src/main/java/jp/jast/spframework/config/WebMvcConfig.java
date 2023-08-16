package jp.jast.spframework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import jp.jast.spframework.GlobalExceptionResolver;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

//	@Autowired
//	HandlerInterceptor requestInterceptor;
	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(requestInterceptor);
//	}

	@Autowired GlobalExceptionResolver globalExceptionResolver;
}
