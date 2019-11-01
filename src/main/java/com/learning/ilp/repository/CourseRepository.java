package com.learning.ilp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.ilp.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

	public Course findByCourseId(long courseId);
}
