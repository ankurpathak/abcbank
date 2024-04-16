package com.github.ankurpathak.customerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
public class CustomerServiceApplication {

	@Bean
	CommonsRequestLoggingFilter logger(){
		final var filter = new CommonsRequestLoggingFilter();
		filter.setIncludeHeaders(true);
		filter.setIncludeClientInfo(true);
		filter.setIncludePayload(true);
		filter.setIncludeQueryString(true);
		return filter;
	}

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

}
