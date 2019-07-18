package com.alpok.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import com.alpok.news.endpoints.HeadlineEmployeesController;
import com.alpok.news.endpoints.HeadlinesController;

@SpringBootApplication
@RestController
public class Application {


	public static void main(String[] args) {
    	
    	Class endpoints[] = {HeadlinesController.class, HeadlineEmployeesController.class};
        SpringApplication.run(endpoints, args);
    }

}
