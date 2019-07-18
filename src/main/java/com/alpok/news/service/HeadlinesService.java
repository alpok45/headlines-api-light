package com.alpok.news.service;

import java.time.LocalDate;
import java.util.List;

import com.alpok.news.api.data.Headline;

public interface HeadlinesService {
	
	public List<Headline>getAllHeadlinesByDate(LocalDate date);

}
