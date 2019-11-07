package com.learning.ilp.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "courseid")
	private long courseId;
	private String courseTitle;
	@Column(name = "coursedescription",columnDefinition = "TEXT")
	private String courseDescription;
	private long courseFee;
	private String faculty;
	private String duration;
	private String coupon;
	@Column(columnDefinition = "varchar(255) default 'enable'")
	private String disabled;
	/*
	 * @ElementCollection
	 * 
	 * @CollectionTable
	 * 
	 * @Column(name = "syllabus",columnDefinition = "TEXT") private List<String>
	 * syllabus;
	 */
	@Column(name = "syllabus",columnDefinition = "TEXT")
	private String syllabus;

	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	public long getCourseId() {
		return courseId;
	}
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	public long getCourseFee() {
		return courseFee;
	}
	public void setCourseFee(long courseFee) {
		this.courseFee = courseFee;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	public String getSyllabus() {
		return syllabus;
	}
	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	
}
