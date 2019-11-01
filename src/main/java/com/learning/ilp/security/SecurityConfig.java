package com.learning.ilp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.learning.ilp.constants.ILPConstants;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private IlpUserDetailService ilpUserDetailService;

	//Authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(ilpUserDetailService);
	}
	
	//Password Encoding
	@Bean
	public PasswordEncoder getEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	//Authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(ILPConstants.ALL_USER_URL).hasAnyRole(ILPConstants.USER_ACCESS,ILPConstants.ADMIN_ACCESS,ILPConstants.SUPER_ADMIN_ACCESS)
		.antMatchers(ILPConstants.ALL_ADMIN_URL).hasAnyRole(ILPConstants.ADMIN_ACCESS,ILPConstants.SUPER_ADMIN_ACCESS)
		.antMatchers(ILPConstants.ALL_SUPER_ADMIN_URL).hasRole(ILPConstants.SUPER_ADMIN_ACCESS)
        .antMatchers(ILPConstants.LOGIN_PAGE).permitAll()
        .antMatchers(ILPConstants.PERMIT_ALL_USER).permitAll()
		.and()
		.formLogin()
		.loginPage(ILPConstants.LOGIN_PAGE)
		.loginProcessingUrl(ILPConstants.AUTHENTICATE_USER_URL)
		.permitAll()
	.and()
	.logout().permitAll()
	.and()
	.exceptionHandling().accessDeniedPage(ILPConstants.ACCESS_DENIED);
	}
}
