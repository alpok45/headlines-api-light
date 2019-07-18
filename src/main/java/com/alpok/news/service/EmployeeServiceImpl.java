package com.alpok.news.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.alpok.news.api.data.Employee;
import com.alpok.news.api.data.Headline;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	static final Logger logger = LoggerFactory.getLogger(HeadlinesServiceImpl.class);
	
	@Value("${employees.data.file}")
	private String resourseFile;	

	@Override
	public void findAndAddAllEmployeesByDeparment(List<Headline> headlines) {

		for(Headline headline : headlines) {
			headline.setSubscribers(getAllEmployeeByDepartment(headline.getDepartment()));
		}
	}		
	
	@Override
	public List<Employee> getAllEmployeeByDepartment(String department) {
		
		logger.info("search for employees of department=" + department);

		List<Employee> employees = new ArrayList<Employee>();

		try {

			InputStream inputStream = this.getClass().getResourceAsStream(resourseFile);
			Reader resourseReader = new InputStreamReader(inputStream);

			try (BufferedReader br = new BufferedReader(resourseReader)) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	
			    	String[]values = line.split(",");
			    	
			    	if(department.equals(values[4])) {
			    		addEmployee(employees, values);
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
		
		logger.info("employees found =" + employees.size());
		
		return employees;
	}
	
	private void addEmployee(List<Employee>employees, String[]values) {
		
		Employee employee = new Employee();
    	
		employee.setId(values[0]);
		employee.setName(values[1]);
		employee.setLocation(values[2]);
		employee.setJobRole(values[3]);
		employee.setDepartment(values[4]);
    	
    	employees.add(employee);
	}

}
