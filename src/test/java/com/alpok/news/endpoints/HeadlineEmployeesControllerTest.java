package com.alpok.news.endpoints;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.alpok.news.endpoints.HeadlineEmployeesController;

/*
 * Here we test how controller for /headline/department/{department}/employees works
 * It supposed to return employee records if department is match, and return empty set if not.
 * In testWhenSomeResultsExpected() {department} will be replaced by PR, IT and SALES
 * In testWhenSomeResultsNotExpected() {department} will be replaced by NONE
 */

@RunWith(SpringRunner.class)
@WebMvcTest(HeadlineEmployeesController.class)
public class HeadlineEmployeesControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testWhenSomeEmployeesExpectedInResponse() throws Exception {
	
		String prExpectedResult = "[{\"id\":\"3\",\"name\":\"Name3\",\"location\":\"US\",\"jobRole\":\"business partner\",\"department\":\"PR\"}]";
		
		mockMvc.perform(get("/headline/department/PR/employees"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(prExpectedResult));
		
		String itExpectedResult = "[{\"id\":\"1\",\"name\":\"Name1\",\"location\":\"GB\",\"jobRole\":\"full time employee\",\"department\":\"IT\"}]";
		
		mockMvc.perform(get("/headline/department/IT/employees"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(itExpectedResult));
		
		String salesExpectedResult = "[{\"id\":\"4\",\"name\":\"Name4\",\"location\":\"JP\",\"jobRole\":\"full time employee\",\"department\":\"SALES\"}," 
		                            + "{\"id\":\"2\",\"name\":\"Name2\",\"location\":\"BR\",\"jobRole\":\"consultant\",\"department\":\"SALES\"}]";
		
		mockMvc.perform(get("/headline/department/SALES/employees"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(salesExpectedResult));

	}
	
	@Test
	public void testWhenNoEmloyeesExpectedInResponse() throws Exception {
		
		mockMvc.perform(get("/headline/department/NONE/employees"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json("[]"));
	}

}
