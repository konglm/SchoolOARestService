package com.goldeneyes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.goldeneyes.interceptor.AuthInterceptor;

@Configuration
public class AppConfigurer implements WebMvcConfigurer {
	
	@Bean
    public AuthInterceptor getAuthInterceptor(){
        return new AuthInterceptor();
	}
 
	/**
     * 注册自定义拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        
    	InterceptorRegistration registration = registry.addInterceptor(getAuthInterceptor());
       	
    	//将这个controller放行
        registration.excludePathPatterns("/error");
        //拦截全部
        registration.addPathPatterns("/**");
    }
}
