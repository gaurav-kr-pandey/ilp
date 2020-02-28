package com.learning.ilp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.learning.ilp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String username);

	@Query(value="select * from user order by first_name",nativeQuery = true)
	public List<User> getIntrestedUsers();
	
	@Query(value="SELECT * FROM user",nativeQuery = true)
	public List<User> getEnrolledUser();
	
	public User findById(int userId);

	@Query(value="SELECT * FROM user order by first_name",nativeQuery = true)
	public List<User> getPendingApprovalUsers();
	
	@Query(value="select * from user where first_name like :searchString%",nativeQuery = true)
	public List<User> getUsersLike(@Param("searchString") String searchString);
}
