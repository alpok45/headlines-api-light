package com.alpok.news.api.data;

import java.util.ArrayList;
import java.util.List;

public class Headline {

	private String id;
	private String title;
	private String abstractStr;
	private String language;
	private String publicationDate;
	private String author;
	private String department;
	private String subscribersUrlPath;
	private List<Employee>subscribers = new ArrayList<Employee>();
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbstractStr() {
		return abstractStr;
	}

	public void setAbstractStr(String abstractStr) {
		this.abstractStr = abstractStr;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getSubscribersUrlPath() {
		return subscribersUrlPath;
	}

	public void setSubscribersUrlPath(String subscribersUrlPath) {
		this.subscribersUrlPath = subscribersUrlPath;
	}

	public List<Employee> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(List<Employee> subscribers) {
		this.subscribers = subscribers;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
}
