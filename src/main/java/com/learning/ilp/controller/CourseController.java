package com.learning.ilp.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.learning.ilp.entity.Course;
import com.learning.ilp.entity.Home;
import com.learning.ilp.entity.Payment;
import com.learning.ilp.entity.Transaction;
import com.learning.ilp.entity.User;
import com.learning.ilp.repository.TransactionRepository;
import com.learning.ilp.repository.UserRepository;
import com.learning.ilp.security.IlpUserDetails;
import com.learning.ilp.services.CourseServices;
import com.learning.ilp.services.HomeServices;

@Controller
public class CourseController {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private CourseServices courseServices;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/course/{courseId}")
	public String courseDescription(@PathVariable("courseId") long courseId,Model model,Principal principal) {
		Course course=courseServices.getCourse(courseId);
		
			if(principal != null) {
				User user = getUser();
				Set<Course> tempCourse=user.getEnrolled();
				if(!tempCourse.contains(course))
					tempCourse.add(course);
				user.setEnrolled(tempCourse);
				userRepo.save(user);
				Set<Course> visited = user.getEnrolled();
				Set<Payment> payments = user.getPayment();
				if(!payments.isEmpty()) {
					for (Payment payment : payments) {
						visited = visited.stream().filter(c->c.getCourseId() != payment.getCourseId()).collect(Collectors.toSet());
					}
				}
				visited = visited.stream().filter(c->c.getCourseId() != courseId ).collect(Collectors.toSet());
				if(visited.size() > 2)
					model.addAttribute("visited", visited);
			}
		String syllabus[]=new String[1];
		if(course.getSyllabus()!=null && course.getSyllabus()!="" && !course.getSyllabus().equals(null) && !course.getSyllabus().equals(""))
			syllabus = course.getSyllabus().split(";");
		else
			syllabus[0] = "Course will be added soon, meanwhile contact our support team";
		model.addAttribute("syllabus", syllabus);
		model.addAttribute("course", course);
		return "course";
	}
	
	@GetMapping("/course")
	public String getAllCourses(Model model) {
		List<Course> courses=courseServices.getAllCourses();
		courses=courses.stream().filter(c->c.getDisabled().equals("enable")).collect(Collectors.toList());
		model.addAttribute("courses", courses);
		return "allcourses";
	}
	
	@GetMapping("/user/course/{courseId}/enroll")
	public String enrollCourse(@PathVariable("courseId") long courseId,@ModelAttribute("transaction") Transaction transaction,Model model) {
		Course course=courseServices.getCourse(courseId);
		model.addAttribute("course", course);
		return "payment";
	}
	
	@GetMapping("/user/course/{courseId}")
	public String feeDetails(@PathVariable("courseId") long courseId,Model model) {
		Course course=courseServices.getCourse(courseId);
		String syllabus[]=new String[1];
		if(course.getSyllabus()!=null && course.getSyllabus()!="" && !course.getSyllabus().equals(null) && !course.getSyllabus().equals(""))
			syllabus = course.getSyllabus().split(";");
		else
			syllabus[0] = "Course will be added soon, meanwhile contact our support team";
		model.addAttribute("course", course);
		model.addAttribute("syllabus", syllabus);
		return "course";
	}
	
	@PostMapping("/user/course/{courseId}/payment")
	public String paymentPage(Model model,@ModelAttribute("transaction") Transaction transaction,@PathVariable int courseId) {
		long amountPaid=0;
		User user = getUser();
		Course course=courseServices.getCourse(courseId);
		
		if(transactionRepository.existsByTransactionId(transaction.getTransactionId())) {
			model.addAttribute("duplicate", "Invalid : Transaction ID can not be duplicate.");
			model.addAttribute("course", course);
			return "payment";
		}
		
		Set<Course> tempCourse=user.getEnrolled();
		Set<Payment> payment= user.getPayment();
		Payment tempPayment = new Payment();
		
		if(!tempCourse.contains(course))
			tempCourse.add(course);
		user.setEnrolled(tempCourse);
		
		
		Iterator<Payment> itr = payment.iterator();
		while(itr.hasNext()) {
			Payment p = itr.next();
			if(p.getCourseId()==courseId && p.getTransaction().getIsValid())
				amountPaid=amountPaid+p.getTransaction().getAmount();
		}
		tempPayment.setActualAmount(course.getCourseFee());
		tempPayment.setAmountPaid(amountPaid);
		tempPayment.setBalancedAmount(course.getCourseFee()-amountPaid);
		String time = LocalDate.now().toString();
		transaction.setTime(time);
		tempPayment.setTransaction(transaction);
		tempPayment.setCourseId(courseId);
		tempPayment.setCourseTitle(course.getCourseTitle());
		payment.add(tempPayment);
		user.setPayment(payment);
		user.setEnrolled(tempCourse);
		userRepo.save(user);
		sendTransactionDetails(user);
		sendNotification(user);
		
		model.addAttribute("paymentDetails", tempPayment);
		model.addAttribute("paymentList", payment);
		return "transaction-history";
	}
	
	@GetMapping("/user/course/payments")
	public String paymentHistrory(Model model) {
		long amountPaid=0;
		User user = getUser();
		Set<Payment> tempPaymentList = new HashSet<>();
		Set<Payment> validPayments= user.getPayment().stream().filter(p->p.getTransaction().getIsValid()).collect(Collectors.toSet());
		List<Payment> pendingPaymentList= user.getPayment().stream().filter(p->!(p.getTransaction().getIsValid())).collect(Collectors.toList());
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
			Course course=courseServices.getCourse(courseId);
			addToList.setCourseId(course.getCourseId());
			addToList.setCourseTitle(course.getCourseTitle());
			addToList.setActualAmount(course.getCourseFee());
			addToList.setAmountPaid(amountPaid);
			addToList.setBalancedAmount(course.getCourseFee()-amountPaid);
			tempPaymentList.add(addToList);
			amountPaid=0;
		}
		model.addAttribute("pendingPaymentList",pendingPaymentList);
		model.addAttribute("paymentList", tempPaymentList);
		return "payment-history";
	}
	
	private void sendTransactionDetails(User user) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("info@ilpeducation.in");
        msg.setTo(user.getEmail());
        msg.setBcc("posttorahuldixit@gmail.com","gaurav17p@gmail.com");
        msg.setSubject("Registration | ILP Education");
        msg.setText("Hi, "+user.getFirstName()+" "+user.getLastName()+" \r\n"
        		+"Your transaction id has been recieved successfully. "
        		+ "Once we approve your transaction, we will let you know as soon as possible. To know more details you can login to www.ilpeducation.in/login and click on [Payment History] tab."
        		+ "\r\n"
        		+ "For any help call us at +919654610063.");

        javaMailSender.send(msg);

    }
	
	private void sendNotification(User user) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("info@ilpeducation.in");
        msg.setTo("posttorahuldixit@gmail.com","gaurav17p@gmail.com");
        msg.setSubject(user.getFirstName()+" "+user.getLastName()+" registered into a course");
        msg.setText("Hi, ILP Admin"+" \r\n"+"This is to notify you that a new user "+ user.getFirstName()+" "+user.getLastName()+" has registered into a course  \r\n"
        		+ "Kindly, login to your ILP Application and approve details ASAP. ");

        javaMailSender.send(msg);

    }
	private  User getUser() {
		IlpUserDetails userDetails = (IlpUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepo.findByUsername(userDetails.getUsername());
	}
	
}
