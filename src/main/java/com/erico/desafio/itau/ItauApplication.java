package com.erico.desafio.itau;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.erico.desafio.itau.interceptor.RequestLoggingInterceptor;

@SpringBootApplication
public class ItauApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ItauApplication.class, args);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new RequestLoggingInterceptor()).addPathPatterns();
	}
}
