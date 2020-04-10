package com.goldeneyes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.goldeneyes.IDao") //扫描的mapper
@SpringBootApplication
public class SchoolOaRestServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SchoolOaRestServiceApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SchoolOaRestServiceApplication.class);
    }

}
