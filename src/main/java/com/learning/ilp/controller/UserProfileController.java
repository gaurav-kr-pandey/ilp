package com.learning.ilp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	private JavaMailSender javaMailSender;
	
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
			List<College> colleges = collegeServices.getAllCollege();
			model.addAttribute("colleges", colleges);
			model.addAttribute("user",user);
			model.addAttribute("msg","Password and Confirm Password must be same.");
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
		List<College> colleges = collegeServices.getAllCollege();
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
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("colleges", colleges);
			model.addAttribute("user", userTemp);
			model.addAttribute("passValid", "Password and confirm password must match.");
			return "update-user-profile";
		}
		
		model.addAttribute("colleges", colleges);
		model.addAttribute("user", userTemp);
		userRepository.save(userTemp);
		model.addAttribute("msg", "Profile Updated Successfully.");
		return "update-user-profile";
	}
	
	@GetMapping("/login/password/{username}")
	public String forgotPassword(@PathVariable("username") String username,Model model) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			model.addAttribute("msg", "Incorrect username. Contact @ +919654610063.");
			return "login";
		}
		else {
			SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setFrom("info@ilpeducation.in");
	        msg.setTo(user.getEmail());
	        msg.setSubject("Your Password | ILP");
	        msg.setText("Hi, "+user.getFirstName()+" "+user.getLastName()+" \r\n"+"Your username and password is :"
	        		+ "\r\n"
	        		+ "Username : "+user.getUsername()+"\r\n"
	        		+"Password : "+user.getPassword()+"\r\n"
	        		+"If this is not you, change your password or inform at info@ilpeducation.in");

	        javaMailSender.send(msg);
		}
        
        Home home=homeServices.careerCards();
		model.addAttribute("home", home);
		model.addAttribute("msg", "Password sent at your email associated with "+username);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(!(auth instanceof AnonymousAuthenticationToken) )
			return "redirect:/";
		return "login";
    }
	
	private  User getUser() {
		IlpUserDetails userDetails = (IlpUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findByUsername(userDetails.getUsername());
	}
}
