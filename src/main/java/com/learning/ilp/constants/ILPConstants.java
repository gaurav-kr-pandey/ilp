package com.learning.ilp.constants;

public class ILPConstants {

	/*
	 *  AdminController Mapping Constants 
	 */
	
	public static final String CREATE_USER = "/create-user";
	
	public static final String APPROVE_PAGE = "/approve";
	
	public static final String CARD_PAGE = "/approve/{userId}/{courseId}";
	
	/*
	 *  CourseController Mapping Constants
	 */
	public static final String COURSE_DESCRIPTION = "/course/{courseId}";
	
	public static final String GET_ALL_COURSES = "/course";
	
	public static final String INTRESTES_IN_COURSE = "/course/{courseId}/enroll";
	
	public static final String PAYMENT_PAGE = "/course/{courseId}/payment";
	
	/*
	 * 	HomeController Mapping Constants
	 */
	public static final String HOME_URL = "/";
	
	public static final String ABOUT_US = "/about";
	
	public static final String CAREERS = "/career";
	
	/*
	 * 	UserProfileController Mapping Constants
	 */
	public static final String SIGNUP_PAGE = "/signup";
	
	public static final String SIGNUP_FORM = "/signup";
	
	public static final String LOGIN_PAGE = "/login";
	
	public static final String ACCESS_DENIED = "/access-denied";
	
	/*
	 *  Spring Security Configuration Constants
	 */
	public static final String SUPER_ADMIN_ACCESS = "SUPER_ADMIN";
	
	public static final String ADMIN_ACCESS = "ADMIN";
	
	public static final String USER_ACCESS = "USER";
	
	public static final String ALL_ADMIN_URL = "/admin/**";
	
	public static final String ALL_SUPER_ADMIN_URL = "/sadmin/**";
	
	public static final String ALL_USER_URL = "/course/**";
	
	public static final String AUTHENTICATE_USER_URL = "/authenticateTheUser";
	
	public static final String PERMIT_ALL_USER = "/**";
	
	
}
