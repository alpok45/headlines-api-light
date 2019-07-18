package com.alpok.news.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alpok.news.api.data.Employee;
import com.alpok.news.api.data.Headline;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class EmployeeServiceImplTest {
	
/*
 
	Test when we have following data in src/test/resources/employees_test.txt we have
		
	4,Name4,JP,full time employee,SALES
	2,Name2,BR,consultant,SALES
	1,Name1,GB,full time employee,IT
	3,Name3,US,business partner,PR	
	
 */

	@Autowired
	private EmployeeService employeeService;
	
	@Test
	public void getAllEmployeeByDepartment() {

		List<Employee> salesEmployees = employeeService.getAllEmployeeByDepartment("SALES");
		List<Employee> prEmployees = employeeService.getAllEmployeeByDepartment("PR");
		List<Employee> itEmployees = employeeService.getAllEmployeeByDepartment("IT");
		List<Employee> notExistedEmployees = employeeService.getAllEmployeeByDepartment("NONE");
		
		assertEquals(2, salesEmployees.size());
		assertEquals(1, prEmployees.size());
		assertEquals(1, itEmployees.size());
		assertEquals(0, notExistedEmployees.size());
		

		Employee salesEmployee1 = salesEmployees.get(0);
		assertEquals("4", salesEmployee1.getId());
		assertEquals("Name4", salesEmployee1.getName());
		assertEquals("JP", salesEmployee1.getLocation());
		assertEquals("full time employee", salesEmployee1.getJobRole());
		assertEquals("SALES", salesEmployee1.getDepartment());
		
		Employee salesEmployee2 = salesEmployees.get(1);
		assertEquals("2", salesEmployee2.getId());
		assertEquals("Name2", salesEmployee2.getName());
		assertEquals("BR", salesEmployee2.getLocation());
		assertEquals("consultant", salesEmployee2.getJobRole());
		assertEquals("SALES", salesEmployee2.getDepartment());
		
		Employee itEmployee = itEmployees.get(0);
		assertEquals("1", itEmployee.getId());
		assertEquals("Name1", itEmployee.getName());
		assertEquals("GB", itEmployee.getLocation());
		assertEquals("full time employee", itEmployee.getJobRole());
		assertEquals("IT", itEmployee.getDepartment());
		
		Employee prEmployee = prEmployees.get(0);
		assertEquals("3", prEmployee.getId());
		assertEquals("Name3", prEmployee.getName());
		assertEquals("US", prEmployee.getLocation());
		assertEquals("business partner", prEmployee.getJobRole());
		assertEquals("PR", prEmployee.getDepartment());
		
	}
	
	@Test
	public void findAndAddAllEmployeesByDeparment() {
		
		/*
		 * Here we create list of 4 headlines,
		 * and assign department names
		 */
		List<Headline>headlines = new ArrayList<Headline>();
		
		headlines.add(new Headline());
		headlines.add(new Headline());
		headlines.add(new Headline());
		headlines.add(new Headline());
		
		int salesIndex = 0;
		int itIndex = 1;
		int prIndex = 2;
		int noneIndex = 3;
		
		headlines.get(salesIndex).setDepartment("SALES");
		headlines.get(itIndex).setDepartment("IT");
		headlines.get(prIndex).setDepartment("PR");
		headlines.get(noneIndex).setDepartment("NONE");
		
        // Before searching for subscribers, check that subscribers list is empty
		assertEquals(0, headlines.get(salesIndex).getSubscribers().size());
		assertEquals(0, headlines.get(itIndex).getSubscribers().size());
		assertEquals(0, headlines.get(prIndex).getSubscribers().size());
		assertEquals(0, headlines.get(noneIndex).getSubscribers().size());
		
		/*
		 * Here we trying to find matched employees by department in employees.txt and attach them to headline as subscribers.
		 * We expect some employees found for SALES, IT, PR and not expect any for NONE
		 */
		employeeService.findAndAddAllEmployeesByDeparment(headlines);

		assertEquals(2, headlines.get(salesIndex).getSubscribers().size());
		assertEquals(1, headlines.get(itIndex).getSubscribers().size());
		assertEquals(1, headlines.get(prIndex).getSubscribers().size());
		assertEquals(0, headlines.get(noneIndex).getSubscribers().size());
		
	}

}
