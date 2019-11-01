package com.learning.ilp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.learning.ilp.constants.ILPConstants;
import com.learning.ilp.entity.College;
import com.learning.ilp.entity.Home;
import com.learning.ilp.entity.User;
import com.learning.ilp.repository.UserRepository;
import com.learning.ilp.security.IlpUserDetails;
import com.learning.ilp.services.CollegeServices;
import com.learning.ilp.services.HomeServices;

@Controller
public class UserProfileController {
	
	@Autowired
	private HomeServices homeServices;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CollegeServices collegeServices;

	@GetMapping(ILPConstants.SIGNUP_PAGE)
	public String signUpPage(@ModelAttribute("user") User user,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(!(auth instanceof AnonymousAuthenticationToken))
			return "redirect:/";
		List<College> colleges = collegeServices.getAllCollege();
		model.addAttribute("colleges", colleges);
		return "sign-up";
	}
	
	@PostMapping(ILPConstants.SIGNUP_FORM)
	public String signup(@ModelAttribute("user") User user,Model model) {
		College clg = collegeServices.getCollege(user.getColgId());
		user.setCollege(clg);
		String username=user.getUsername();
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("user",user);
			model.addAttribute("msg","Error : Password and Confirm Password must be same.");
			return "sign-up";
		}
		if(userRepository.findByUsername(username)==null) {
			if(user.getRoles()==null || user.getRoles() == "")
				user.setRoles("ROLE_USER");
			userRepository.save(user);
		}
		else {
			model.addAttribute("user",user);
			model.addAttribute("userNameExists", "'"+user.getUsername()+"'"+" not available.");
			return "sign-up";
		}
		return "redirect:/login";
	}
	
	@GetMapping(ILPConstants.LOGIN_PAGE)
	public String login(Model model) {
		Home home=homeServices.careerCards();
		model.addAttribute("home", home);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(!(auth instanceof AnonymousAuthenticationToken) )
			return "redirect:/";
		return "login";
	}
	
	@GetMapping("/update/user")
	public String updateProfilePage(@ModelAttribute("user") User user,Model model) {
		user = getUser();
		List<College> colleges = collegeServices.getAllCollege();
		model.addAttribute("colleges", colleges);
		model.addAttribute("user", user);
		return "update-user-profile";
	}
	
	@PostMapping("/update/user/profile")
	public String updateUserProfile(@ModelAttribute("user") User user,Model model) {
		College clg = collegeServices.getCollege(user.getColgId());
		user.setCollege(clg);
		User userTemp= getUser();
		userTemp.setAlternatePhoneNumber(user.getAlternatePhoneNumber());
		userTemp.setCollege(user.getCollege());
		userTemp.setConfirmPassword(user.getConfirmPassword());
		userTemp.setCoupon(user.getCoupon());
		userTemp.setEmail(user.getEmail());
		userTemp.setFirstName(user.getFirstName());
		userTemp.setLastName(user.getLastName());
		userTemp.setPassword(user.getPassword());
		userTemp.setPhoneNumber(user.getPhoneNumber());
		userTemp.setRegistrationType(user.getRegistrationType());
		userTemp.setColgId(user.getColgId());
		List<College> colleges = collegeServices.getAllCollege();
		model.addAttribute("colleges", colleges);
		userRepository.save(userTemp);
		return "redirect:/";
	}
	
	private  User getUser() {
		IlpUserDetails userDetails = (IlpUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findByUsername(userDetails.getUsername());
	}
}
