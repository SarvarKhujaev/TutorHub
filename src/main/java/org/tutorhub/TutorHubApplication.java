package org.tutorhub;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TutorHubApplication {
	public static ApplicationContext context;

	public static void main( final String[] args ) {
		context = SpringApplication.run( TutorHubApplication.class, args );
	}
}
