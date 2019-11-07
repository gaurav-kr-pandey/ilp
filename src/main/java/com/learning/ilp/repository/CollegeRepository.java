package com.learning.ilp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.ilp.entity.College;

@Repository
public interface CollegeRepository extends JpaRepository<College, Integer> {

	public College findById(int id);

	public College findByCollegeId(int collegeId);
}
