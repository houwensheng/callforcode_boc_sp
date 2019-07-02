package com.bocsoft.callforcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages= {"com.bocsoft.callforcode"})
@EnableScheduling
public class CallForCodeClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(CallForCodeClientApplication.class, args);		
	}
}
