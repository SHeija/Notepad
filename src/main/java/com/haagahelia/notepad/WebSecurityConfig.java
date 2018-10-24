package com.haagahelia.notepad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.haagahelia.notepad.web.UserDetailService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailService userDetailService;
	
	@Override
	protected void configure (HttpSecurity http) throws Exception {
		http
		 .authorizeRequests().antMatchers("/css/**").permitAll() //enables css when logged out
		 .and()
		 .authorizeRequests().antMatchers("/signup", "/saveuser").permitAll()
		 .and()
		 .authorizeRequests()
		 	.anyRequest().authenticated()
		 	.and()
		 .formLogin()
		 	.loginPage("/login")
		 	.defaultSuccessUrl("/noteslist")
		 	.permitAll()
		 	.and()
	 	 .logout()
	 	 	.permitAll();	 	
		 
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
