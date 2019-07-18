package com.alpok.news.endpoints;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpok.news.api.data.Employee;
import com.alpok.news.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.alpok.news.service")
public class HeadlineEmployeesController {
	
	static final Logger logger = LoggerFactory.getLogger(HeadlineEmployeesController.class);
	
	@Autowired
	private EmployeeService employeeService;
	
    @RequestMapping(value = "${department.employees.url.path}", produces = "application/json")
    public String employeesByDepartment(@PathVariable String department) {
    	
    	logger.info("search for employees of department=" + department);
    	
    	List<Employee>employees = employeeService.getAllEmployeeByDepartment(department);
    	
    	logger.info("employees found =" + employees.size());
    	
    	ObjectMapper jsonMapper = new ObjectMapper();
    	
    	try {
			return jsonMapper.writeValueAsString(employees);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
    }		

}
