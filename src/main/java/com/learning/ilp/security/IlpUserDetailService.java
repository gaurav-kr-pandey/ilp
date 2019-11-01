package com.learning.ilp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learning.ilp.entity.User;
import com.learning.ilp.repository.UserRepository;

@Service
public class IlpUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user =userRepository.findByUsername(userName);
		if(user==null) {
			throw new UsernameNotFoundException("User not found.");
		}
		return new IlpUserDetails(user);
	}

}
