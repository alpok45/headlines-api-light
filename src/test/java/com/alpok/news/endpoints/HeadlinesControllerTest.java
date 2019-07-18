package com.alpok.news.endpoints;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.alpok.news.endpoints.HeadlinesController;

@RunWith(SpringRunner.class)
@WebMvcTest(HeadlinesController.class)
public class HeadlinesControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	/*
	 * There are two headlines in repository for date 2019-05-22,
	 * we expect have them in response with out any subscribers attached 
	 */
	@Test
	public void testWhenHeadlinesForDateExistInRepository() throws Exception {
		
		String twoExpectedHeadlinesWithoutSubscribers = 
				"[{\"id\":\"2\",\"title\":\"NewsTitle2\",\"abstractStr\":null,\"language\":\"ENG\",\"publicationDate\":\"2019-05-22\",\"author\":null,\"department\":\"SALES\",\"subscribersUrlPath\":\"/headline/department/SALES/employees\",\"subscribers\":[]},"
			   + "{\"id\":\"3\",\"title\":\"NewsTitle3\",\"abstractStr\":null,\"language\":\"RUS\",\"publicationDate\":\"2019-05-22\",\"author\":null,\"department\":\"FINANCE\",\"subscribersUrlPath\":\"/headline/department/FINANCE/employees\",\"subscribers\":[]}]";		
	
		mockMvc.perform(get("/headlines?date=2019-05-22&includeEmployees=false"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(twoExpectedHeadlinesWithoutSubscribers));
	}	
	
	/*
	 * Test when repository has one headline for date and one subscriber, and we want to include subscriber.
	 * Expected on one headline in response with one subscribers attached
	 */
	@Test
	public void testWhenOneHeadlinesForDateExistAndItHasOneSubsciber() throws Exception {
		
		String oneHeadlineWithOneSubscriber = 
				"[{\"id\":\"1\",\"title\":\"NewsTitle1\",\"abstractStr\":null,\"language\":\"JPN\",\"publicationDate\":\"2019-05-25\",\"author\":null,\"department\":\"PR\",\"subscribersUrlPath\":\"/headline/department/PR/employees\","
				+ "\"subscribers\":[{\"id\":\"3\",\"name\":\"Name3\",\"location\":\"US\",\"jobRole\":\"business partner\",\"department\":\"PR\"}]}]";		
	
		mockMvc.perform(get("/headlines?date=2019-05-25&includeEmployees=true"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(oneHeadlineWithOneSubscriber));
	}	

	/*
	 * Test when repository has one headline for date and one subscriber, but we do not want to include subscriber.
	 * Expected on one headline in response with empty subscribers set
	 */
	@Test
	public void testWhenOneHeadlinesForDateExistAndItHasOneSubsciberButNotIncluded() throws Exception {
		
		String oneHeadlineWithOneSubscriber = 
				"[{\"id\":\"1\",\"title\":\"NewsTitle1\",\"abstractStr\":null,\"language\":\"JPN\",\"publicationDate\":\"2019-05-25\",\"author\":null,\"department\":\"PR\",\"subscribersUrlPath\":\"/headline/department/PR/employees\","
				+ "\"subscribers\":[]}]";		
	
		mockMvc.perform(get("/headlines?date=2019-05-25&includeEmployees=false"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(oneHeadlineWithOneSubscriber));
	}	
	
	/*
	 * Test when headline in repository but no matched employees with parameter includeEmployees=true
	 * Expected on one headline in response with empty subscribers set
	 */
	@Test
	public void testWhenOneHeadlinesForDateExistButNoSubscribersWithInclude() throws Exception {
		
		String oneHeadlineWithOneSubscriber = 
				"[{\"id\":\"4\",\"title\":\"NewsTitle4\",\"abstractStr\":null,\"language\":\"ENG\",\"publicationDate\":\"2019-05-23\",\"author\":null,\"department\":\"HR\",\"subscribersUrlPath\":\"/headline/department/HR/employees\",\"subscribers\":[]}]";		
	
		mockMvc.perform(get("/headlines?date=2019-05-23&includeEmployees=true"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(oneHeadlineWithOneSubscriber));
	}
	
	
	/*
	 * Almost same as previous test, except parameter includeEmployees value.
	 * Test when headline in repository but no matched employees with parameter includeEmployees=false
	 * Expected on one headline in response with empty subscribers set
	 */
	@Test
	public void testWhenOneHeadlinesForDateExistButNoSubscribersWithNoInclude() throws Exception {
		
		String oneHeadlineWithOneSubscriber = 
				"[{\"id\":\"4\",\"title\":\"NewsTitle4\",\"abstractStr\":null,\"language\":\"ENG\",\"publicationDate\":\"2019-05-23\",\"author\":null,\"department\":\"HR\",\"subscribersUrlPath\":\"/headline/department/HR/employees\",\"subscribers\":[]}]";		
	
		mockMvc.perform(get("/headlines?date=2019-05-23&includeEmployees=false"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(oneHeadlineWithOneSubscriber));
	}	
	
	/*
	 * There is no headlines in repository for 2019-05-24
	 * Expected empty set in return with includeEmployees parameter and without it
	 */
	@Test
	public void testWhenNoHeadlinesForProvidedDate() throws Exception {
		
		mockMvc.perform(get("/headlines?date=2019-05-24&includeEmployees=false"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json("[]"));
		
		mockMvc.perform(get("/headlines?date=2019-05-24&includeEmployees=true"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json("[]"));
		
		mockMvc.perform(get("/headlines?date=2019-05-24"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json("[]"));
	}	

}
