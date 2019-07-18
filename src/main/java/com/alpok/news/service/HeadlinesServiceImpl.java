package com.alpok.news.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.alpok.news.api.data.Headline;

@Service
public class HeadlinesServiceImpl implements HeadlinesService {

	static final Logger logger = LoggerFactory.getLogger(HeadlinesServiceImpl.class);
	
	@Value("${headlines.data.file}")
	private String resourseFile;
	
	@Value("${department.employees.url.path}")
	private String departmentEmployeesUrlPath;

	@Override
	public List<Headline> getAllHeadlinesByDate(LocalDate date) {
		
		logger.info("search for headlines of date=" + date);
		
		List<Headline>headlines = new ArrayList<Headline>();
		
		try {

			InputStream inputStream = this.getClass().getResourceAsStream(resourseFile);
			Reader resourseReader = new InputStreamReader(inputStream);

			try (BufferedReader br = new BufferedReader(resourseReader)) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	
			    	String[]values = line.split(",");
			    	
			    	if(date.toString().equals(values[1])) {
			    		addHeadline(headlines, values);
			    	}

			    }
			}
		} catch (FileNotFoundException e) {
			logger.error("IOException");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IOException");
			e.printStackTrace();
		}
		
		logger.info("headlines found = " + headlines.size());
		
		return headlines;
	}
	
	private void addHeadline(List<Headline>headlines, String[]values) {
		
    	Headline headline = new Headline();
    	
    	headline.setId(values[0]);
    	headline.setPublicationDate(values[1]);
    	headline.setTitle(values[2]);
    	headline.setLanguage(values[3]);
    	headline.setDepartment(values[4]);
    	headline.setSubscribersUrlPath(departmentEmployeesUrlPath.replaceFirst("\\{department\\}", values[4]));
    	
    	headlines.add(headline);
	}


}
