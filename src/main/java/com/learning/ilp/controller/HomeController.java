package com.learning.ilp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.learning.ilp.entity.Home;
import com.learning.ilp.entity.User;
import com.learning.ilp.repository.UserRepository;
import com.learning.ilp.services.HomeServices;

@Controller
public class HomeController {

	@Autowired
	private HomeServices homeServices;
	
	@Autowired
	private UserRepository userRepository;
	@GetMapping("/")
	public String home(@ModelAttribute("user") User user,Model model) {
		Home home=homeServices.startHome();
		List<User> userList = userRepository.findAll();
		if(userList.isEmpty() || userList==null )
			return "first-time-profile";
		model.addAttribute("home", home);
		return "welcome";
	}
	
	@GetMapping("/about")
	public String aboutUs(Model model) {
		return "about";
	}
	
	@GetMapping("/career")
	public String careerPrograms(Model model) {
		Home home=homeServices.careerCards();
		model.addAttribute("home", home);
		return "career";
	}
	
}
