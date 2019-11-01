package com.learning.ilp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.learning.ilp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String username);

	@Query(value="SELECT * FROM user",nativeQuery = true)
	public List<User> getIntrestedUsers();
	
	@Query(value="SELECT * FROM user",nativeQuery = true)
	public List<User> getEnrolledUser();
	
	public User findById(int userId);
}
