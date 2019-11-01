package com.learning.ilp.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learning.ilp.entity.Cards;
import com.learning.ilp.entity.College;
import com.learning.ilp.entity.Course;
import com.learning.ilp.entity.Home;
import com.learning.ilp.entity.Payment;
import com.learning.ilp.entity.Transaction;
import com.learning.ilp.entity.User;
import com.learning.ilp.repository.CardsRepository;
import com.learning.ilp.repository.CourseRepository;
import com.learning.ilp.repository.PaymentRepository;
import com.learning.ilp.repository.TransactionRepository;
import com.learning.ilp.repository.UserRepository;
import com.learning.ilp.security.IlpUserDetails;
import com.learning.ilp.services.CollegeServices;

@Secured("ROLE_SUPER_ADMIN")
@Controller
public class AdminController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private CollegeServices collegeServices;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private CardsRepository cardsRepo;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@GetMapping("/create-user")
	public String createUser(@ModelAttribute("user") User user,Model model) {
		List<College> colleges = collegeServices.getAllCollege();
		model.addAttribute("colleges", colleges);
		return "sign-up";
	}
	
	@GetMapping("/approve")
	public String approve(@ModelAttribute("user") User user,Model model) {
		List<User> allUsers=userRepository.getIntrestedUsers();
		List<User> intrestedUsers=allUsers.stream().filter(u->u.getPayment().isEmpty()).collect(Collectors.toList());
		model.addAttribute("intrestedUsers", intrestedUsers);
		List<User> paidUsers=allUsers.stream().filter(u->!u.getPayment().isEmpty()).collect(Collectors.toList());
		model.addAttribute("paidUsers", paidUsers);
		return "approve";
	}
	
	@GetMapping("/cards")
	public String cards(@ModelAttribute("card") Cards card,Model model) {
		long cardCount=cardsRepo.findAll().stream().filter(cardTemp->!cardTemp.getIsCareerProgram()).count();
		List<Course> courses = courseRepository.findAll();
		model.addAttribute("courses", courses);
		if(cardCount<3) {
			model.addAttribute("msg", "You can add "+(5-cardCount)+" more cards.");
		}else {
			model.addAttribute("msg", "You can add only Career Programs. Three Cards for home screen has already created.");
		}
		return "cards";
	}
	
	@PostMapping("/cards")
	public String createCards(@ModelAttribute("card") Cards card,Model model) {
		Home home=new Home();
		home.setHomeId(1);
		card.setHome(home);
		model.addAttribute("home", home);
		List<Course> courses = courseRepository.findAll();
		model.addAttribute("courses", courses);
		long cardCount=cardsRepo.findAll().stream().filter(cardTemp->!cardTemp.getIsCareerProgram()).count();
		if(cardCount<5 || card.getIsCareerProgram()) {
			cardsRepo.save(card);
			card=null;
			cardCount=cardsRepo.findAll().stream().filter(cardTemp->!cardTemp.getIsCareerProgram()).count();
			model.addAttribute("msg", "Card Added Successfully.");
		}
		else {
			model.addAttribute("msg", "Already added 5 cards. Delete previous cards to add new cards or Check Career Programs option below.");
		}
		return "cards";
	}
	
	@GetMapping("/card/update-cards")
	public String updateCardPage(@ModelAttribute("card") Cards card,Model model) {
		List<Course> courses = courseRepository.findAll();
		model.addAttribute("courses", courses);
		List<Cards> cards = cardsRepo.findAll();
		model.addAttribute("cards", cards);
		return "update-cards";
	}
	@GetMapping("/update/card/{cardId}")
	public String updateCard(@ModelAttribute("card") Cards card,@PathVariable("cardId") int cardId,Model model) {
		List<Cards> cards = cardsRepo.findAll();
		model.addAttribute("cards", cards);
		List<Course> courses = courseRepository.findAll();
		model.addAttribute("courses", courses);
		Cards tempCard = cardsRepo.findByCardId(cardId);
		model.addAttribute("card", tempCard);
		return "update-cards";
	}
	
	@PostMapping("/card/update")
	public String updateCard(@ModelAttribute("card") Cards card,RedirectAttributes redir) {
		Home home=new Home();
		home.setHomeId(1);
		card.setHome(home);
		cardsRepo.save(card);
		redir.addFlashAttribute("msg","Card with title : "+card.getCardTitle().toUpperCase()+ ", updated successfully.");
		return "redirect:/card/update-cards";
	}
	
	@GetMapping("/course/entry")
	public String course(@ModelAttribute("course") Course course,Model model) {
		return "create-course";
	}
	
	@PostMapping("/course/create")
	public String createCourse(@ModelAttribute("course") Course course,RedirectAttributes redir) {
		course.setDisabled("enable");
		courseRepository.save(course);
		redir.addFlashAttribute("msg","Course with title : "+course.getCourseTitle().toUpperCase()+ ", created successfully.");
		return "redirect:/course/entry";
	}
	
	
	@GetMapping("/course/update-course")
	public String updateCoursePage(@ModelAttribute("course") Course course,Model model) {
		List<Course> courses = courseRepository.findAll();
		model.addAttribute("courses", courses);
		return "update-course";
	}
	@GetMapping("/update/course/{courseId}")
	public String updateCourse(@ModelAttribute("course") Course course,@PathVariable("courseId") long courseId,Model model) {
		List<Course> courses = courseRepository.findAll();
		model.addAttribute("courses", courses);
		Course tempCourse = courseRepository.findByCourseId(courseId);
		model.addAttribute("course", tempCourse);
		return "update-course";
	}
	
	@PostMapping("/course/update")
	public String updateCourse(@ModelAttribute("course") Course course,RedirectAttributes redir) {
		courseRepository.save(course);
		redir.addFlashAttribute("msg","Course with title : "+course.getCourseTitle().toUpperCase()+ ", updated successfully.");
		return "redirect:/course/update-course";
	}
	@GetMapping("/college/entry")
	public String collegePage(@ModelAttribute("college") College college,Model model) {
		return "add-college";
	}
	
	@PostMapping("/college/create")
	public String addCollege(@ModelAttribute("college") College college,RedirectAttributes redir) {
		collegeServices.save(college);
		redir.addFlashAttribute("msg","College with name : "+college.getCollegeName().toUpperCase()+ ", added successfully.");
		return "redirect:/college/entry";
	}
	
	@PostMapping("/configure/user/search")
	public String searchProfile(@ModelAttribute("user") User user,@RequestParam("username") String username,Model model) {
		user=userRepository.findByUsername(username);
		List<College> colleges = collegeServices.getAllCollege();
		model.addAttribute("colleges", colleges);
		model.addAttribute("user", user);
		return "configure-user-profile";
	}
	
	@GetMapping("/configure/user")
	public String updateProfilePage(@ModelAttribute("user") User user,Model model) {
		user = getUser();
		List<College> colleges = collegeServices.getAllCollege();
		model.addAttribute("colleges", colleges);
		model.addAttribute("user", user);
		return "configure-user-profile";
	}
	
	@PostMapping("/configure/user/profile")
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
	
	@GetMapping("/reject/transaction/{userId}/{paymentId}")
	public String rejectTransaction(@PathVariable("userId") int userId,@PathVariable("paymentId") int paymentId,Model model) {
		User user = userRepository.findById(userId);
		paymentRepository.deleteById(paymentId);
		long amountPaid=0;
		user = userRepository.findById(userId);
		Set<Payment> tempPaymentList = new HashSet<>();
		Set<Payment> validPayments= user.getPayment().stream().filter(p->p.getTransaction().getIsValid()).collect(Collectors.toSet());
		Set<Long> courseIds = new HashSet<>();
		
		for(Payment p : validPayments){
			courseIds.add(p.getCourseId());
		}

		for(Long courseId : courseIds){
			for(Payment p : validPayments) {
				if(p.getCourseId()==courseId)
					amountPaid=amountPaid+p.getTransaction().getAmount();
			}
			Payment addToList = new Payment();
			Course course=courseRepository.findByCourseId(courseId);
			addToList.setCourseId(course.getCourseId());
			addToList.setCourseTitle(course.getCourseTitle());
			addToList.setActualAmount(course.getCourseFee());
			addToList.setAmountPaid(amountPaid);
			addToList.setBalancedAmount(course.getCourseFee()-amountPaid);
			tempPaymentList.add(addToList);
			amountPaid=0;
		}
		model.addAttribute("paymentList", tempPaymentList);
			model.addAttribute("userDetails", user);
		return "approve-transaction";
	}
	
	@GetMapping("/approve/transaction/{userId}/{transactionKey}")
	public String approveTransaction(@PathVariable("userId") int userId,@PathVariable("transactionKey") int transactionKey,Model model) {
		User user = userRepository.findById(userId);
		Transaction transaction=transactionRepository.findById(transactionKey);
		transaction.setValid(true);
		transactionRepository.save(transaction);
		long amountPaid=0;
		user = userRepository.findById(userId);
		Set<Payment> tempPaymentList = new HashSet<>();
		Set<Payment> validPayments= user.getPayment().stream().filter(p->p.getTransaction().getIsValid()).collect(Collectors.toSet());
		Set<Long> courseIds = new HashSet<>();
		
		for(Payment p : validPayments){
			courseIds.add(p.getCourseId());
		}

		for(Long courseId : courseIds){
			for(Payment p : validPayments) {
				if(p.getCourseId()==courseId)
					amountPaid=amountPaid+p.getTransaction().getAmount();
			}
			Payment addToList = new Payment();
			Course course=courseRepository.findByCourseId(courseId);
			addToList.setCourseId(course.getCourseId());
			addToList.setCourseTitle(course.getCourseTitle());
			addToList.setActualAmount(course.getCourseFee());
			addToList.setAmountPaid(amountPaid);
			addToList.setBalancedAmount(course.getCourseFee()-amountPaid);
			tempPaymentList.add(addToList);
			amountPaid=0;
		}
		model.addAttribute("paymentList", tempPaymentList);
		model.addAttribute("userDetails", user);
		return "approve-transaction";
	}
	
	@GetMapping("/disapprove/transaction/{userId}/{transactionKey}")
	public String disapproveTransaction(@PathVariable("userId") int userId,@PathVariable("transactionKey") int transactionKey,Model model) {
		User user = userRepository.findById(userId);
		Transaction transaction=transactionRepository.findById(transactionKey);
		transaction.setValid(false);
		transactionRepository.save(transaction);
		long amountPaid=0;
		user = userRepository.findById(userId);
		Set<Payment> tempPaymentList = new HashSet<>();
		Set<Payment> validPayments= user.getPayment().stream().filter(p->p.getTransaction().getIsValid()).collect(Collectors.toSet());
		Set<Long> courseIds = new HashSet<>();
		
		for(Payment p : validPayments){
			courseIds.add(p.getCourseId());
		}

		for(Long courseId : courseIds){
			for(Payment p : validPayments) {
				if(p.getCourseId()==courseId)
					amountPaid=amountPaid+p.getTransaction().getAmount();
			}
			Payment addToList = new Payment();
			Course course=courseRepository.findByCourseId(courseId);
			addToList.setCourseId(course.getCourseId());
			addToList.setCourseTitle(course.getCourseTitle());
			addToList.setActualAmount(course.getCourseFee());
			addToList.setAmountPaid(amountPaid);
			addToList.setBalancedAmount(course.getCourseFee()-amountPaid);
			tempPaymentList.add(addToList);
			amountPaid=0;
		}
		model.addAttribute("paymentList", tempPaymentList);
		model.addAttribute("userDetails", user);
		return "approve-transaction";
	}
	
	@GetMapping("/view/transaction/{id}")
	public String viewTransaction(@PathVariable("id") int id,Model model) {
		long amountPaid=0;
		User user = userRepository.findById(id);
		Set<Payment> tempPaymentList = new HashSet<>();
		Set<Payment> validPayments= user.getPayment().stream().filter(p->p.getTransaction().getIsValid()).collect(Collectors.toSet());
		Set<Long> courseIds = new HashSet<>();
		
		for(Payment p : validPayments){
			courseIds.add(p.getCourseId());
		}

		for(Long courseId : courseIds){
			for(Payment p : validPayments) {
				if(p.getCourseId()==courseId)
					amountPaid=amountPaid+p.getTransaction().getAmount();
			}
			Payment addToList = new Payment();
			Course course=courseRepository.findByCourseId(courseId);
			addToList.setCourseId(course.getCourseId());
			addToList.setCourseTitle(course.getCourseTitle());
			addToList.setActualAmount(course.getCourseFee());
			addToList.setAmountPaid(amountPaid);
			addToList.setBalancedAmount(course.getCourseFee()-amountPaid);
			tempPaymentList.add(addToList);
			amountPaid=0;
		}
		model.addAttribute("paymentList", tempPaymentList);
		model.addAttribute("userDetails", user);
		return "approve-transaction";
	}
	private  User getUser() {
		IlpUserDetails userDetails = (IlpUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findByUsername(userDetails.getUsername());
	}
}
