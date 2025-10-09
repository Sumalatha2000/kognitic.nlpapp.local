package com.kognitic.nlpapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 * @author Gowrisankar
 * @since 11-11-21
 */
@SpringBootApplication
@EnableScheduling
@ImportResource("classpath:gate-beans.xml")
public class KogniticNlpAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(KogniticNlpAppApplication.class, args);
	}
}
