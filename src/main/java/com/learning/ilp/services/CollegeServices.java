package com.learning.ilp.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.ilp.entity.College;
import com.learning.ilp.repository.CollegeRepository;

@Service
public class CollegeServices {

	@Autowired
	private CollegeRepository collegeRepository;
	
	public List<College> getAllCollege(){
		return collegeRepository.findAll();
	}
	
	public College getCollege(int id) {
		return collegeRepository.findById(id);
	}
	public void saveCollege(College college) {
		collegeRepository.save(college);
	}

	public void save(College college) {
		collegeRepository.save(college);
		
	}

	public College findByCollegeId(int collegeId) {
		// TODO Auto-generated method stub
		return collegeRepository.findByCollegeId(collegeId);
	}
}
