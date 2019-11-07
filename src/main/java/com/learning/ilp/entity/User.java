package com.learning.ilp.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name="user")
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String username;
	private String password;
	private String confirmPassword;
	private String roles;
	private String firstName;
	private String lastName;
	private String email;
	private int colgId;
	@OneToOne
	private College college;
	private long phoneNumber;
	@ManyToMany
	private Set<Course> enrolled;
	private long alternatePhoneNumber;
	private String coupon;
	private String registrationType;
	private int passOutYear;
	
	@OneToMany(cascade = CascadeType.ALL)
	Set<Payment> payment;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public College getCollege() {
		return college;
	}
	public void setCollege(College college) {
		this.college = college;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Set<Course> getEnrolled() {
		return enrolled;
	}
	public void setEnrolled(Set<Course> enrolled) {
		this.enrolled = enrolled;
	}
	public long getAlternatePhoneNumber() {
		return alternatePhoneNumber;
	}
	public void setAlternatePhoneNumber(long alternatePhoneNumber) {
		this.alternatePhoneNumber = alternatePhoneNumber;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getRegistrationType() {
		return registrationType;
	}
	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}
	public int getColgId() {
		return colgId;
	}
	public void setColgId(int colgId) {
		this.colgId = colgId;
	}
	public Set<Payment> getPayment() {
		return payment;
	}
	public void setPayment(Set<Payment> payment) {
		this.payment = payment;
	}
	public int getPassOutYear() {
		return passOutYear;
	}
	public void setPassOutYear(int passOutYear) {
		this.passOutYear = passOutYear;
	}
	
}
