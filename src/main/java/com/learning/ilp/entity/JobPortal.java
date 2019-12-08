package com.learning.ilp.entity;

public class JobPortal {

	private long id;
	private String company;
	private String title;
	private String description;
	private String experienceRequired;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getExperienceRequired() {
		return experienceRequired;
	}
	public void setExperienceRequired(String experienceRequired) {
		this.experienceRequired = experienceRequired;
	}
	
	
}
