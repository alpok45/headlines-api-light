package com.alpok.news.service;

import java.util.List;

import com.alpok.news.api.data.Employee;
import com.alpok.news.api.data.Headline;

public interface EmployeeService {
	
	public List<Employee>getAllEmployeeByDepartment(String department);
	public void findAndAddAllEmployeesByDeparment(List<Headline> headlines);

}
