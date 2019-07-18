package com.alpok.news.endpoints;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpok.news.api.data.Headline;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.alpok.news.service.EmployeeService;
import com.alpok.news.service.HeadlinesService;

@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.alpok.news.service")
public class HeadlinesController {
	
	static final Logger logger = LoggerFactory.getLogger(HeadlinesController.class);
	
	@Autowired
	private HeadlinesService headlinesService;
	
	@Autowired
	private EmployeeService employeeService;

    @RequestMapping(value = "/headlines", produces = "application/json")
    public String headlines(@RequestParam("date") @DateTimeFormat(iso = ISO.DATE) LocalDate date,
    		                @RequestParam(value = "includeEmployees", required=false, defaultValue = "true") boolean includeEmployees) {
    	
    	logger.info("search for headlines of date=" + date + ", includeEmployees=" + includeEmployees);
    	
    	List<Headline> headlines = headlinesService.getAllHeadlinesByDate(date);
    	
    	logger.info("headlines found = " + headlines.size());
    	
    	if(includeEmployees) {
    		employeeService.findAndAddAllEmployeesByDeparment(headlines);
    	}

    	ObjectMapper jsonMapper = new ObjectMapper();

    	try {
			return jsonMapper.writeValueAsString(headlines);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}

    }

}
