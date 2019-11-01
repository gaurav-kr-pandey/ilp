package com.learning.ilp.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.ilp.entity.Course;
import com.learning.ilp.repository.CourseRepository;

@Service
public class CourseServices {

	@Autowired
	private CourseRepository courseRepository;
	public Course getCourse(long courseId) {
		return courseRepository.findByCourseId(courseId);
	}
	
	public List<Course> getAllCourses(){
		return courseRepository.findAll();
	}
}
