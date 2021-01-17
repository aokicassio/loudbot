package com.stonks.loudbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableScheduling
public class LoudbotApplication {

	@Value("${api.cryptocompare.url}")
	private String cryptocompareUrl;

	public static void main(String[] args) {
		SpringApplication.run(LoudbotApplication.class, args);
	}

	@Bean
	public WebClient webClient(){
		return WebClient
				.builder()
				.baseUrl(cryptocompareUrl)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

}
