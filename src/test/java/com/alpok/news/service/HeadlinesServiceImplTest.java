package com.alpok.news.service;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alpok.news.api.data.Headline;

/*

	Test when we have following data in src/test/resources/headlines_test.txt we have
	
	1,2019-05-25,NewsTitle1,JPN,PR
	2,2019-05-22,NewsTitle2,ENG,SALES
	3,2019-05-22,NewsTitle3,RUS,FINANCE
	4,2019-05-23,NewsTitle4,ENG,HR

 */

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class HeadlinesServiceImplTest {

	@Autowired
	private HeadlinesService headlinesService;
	
	@Test
	public void getAllHeadlinesByDateWhenHeadlinesExist() {	
		
		List<Headline> headlines1 = headlinesService.getAllHeadlinesByDate(LocalDate.parse("2019-05-22"));
		
		assertEquals(2, headlines1.size());

		assertEquals("2", headlines1.get(0).getId());
		assertEquals("2019-05-22", headlines1.get(0).getPublicationDate());
		assertEquals("NewsTitle2", headlines1.get(0).getTitle());
		assertEquals("ENG", headlines1.get(0).getLanguage());
		assertEquals("SALES", headlines1.get(0).getDepartment());
		
		assertEquals("3", headlines1.get(1).getId());
		assertEquals("2019-05-22", headlines1.get(1).getPublicationDate());
		assertEquals("NewsTitle3", headlines1.get(1).getTitle());
		assertEquals("RUS", headlines1.get(1).getLanguage());
		assertEquals("FINANCE", headlines1.get(1).getDepartment());
		
		List<Headline> headlines2 = headlinesService.getAllHeadlinesByDate(LocalDate.parse("2019-05-23"));
		
		assertEquals(1, headlines2.size());
		
		assertEquals("4", headlines2.get(0).getId());
		assertEquals("2019-05-23", headlines2.get(0).getPublicationDate());
		assertEquals("NewsTitle4", headlines2.get(0).getTitle());
		assertEquals("ENG", headlines2.get(0).getLanguage());
		assertEquals("HR", headlines2.get(0).getDepartment());
		
		List<Headline> headlines3 = headlinesService.getAllHeadlinesByDate(LocalDate.parse("2019-05-25"));
		
		assertEquals(1, headlines3.size());
		
		assertEquals("1", headlines3.get(0).getId());
		assertEquals("2019-05-25", headlines3.get(0).getPublicationDate());
		assertEquals("NewsTitle1", headlines3.get(0).getTitle());
		assertEquals("JPN", headlines3.get(0).getLanguage());
		assertEquals("PR", headlines3.get(0).getDepartment());

	}

	@Test
	public void getAllHeadlinesByDateWhenHeadlinesNotExist() {

		List<Headline> headlines4 = headlinesService.getAllHeadlinesByDate(LocalDate.parse("2019-05-24"));

		assertEquals(0, headlines4.size());
	}

}
