package com.learning.ilp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.ilp.entity.Home;

@Repository
public interface HomeRepository extends JpaRepository<Home, Integer> {

}
