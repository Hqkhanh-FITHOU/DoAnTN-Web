package com.graduate.hou;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.graduate.hou.service.StorageService;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //schedule
public class HouApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HouApplication.class, args);
	}

	@SuppressWarnings("unused")
	@Bean
	CommandLineRunner init(StorageService storageService){
		return (arg) -> {
			storageService.init();
		};
	}
}
