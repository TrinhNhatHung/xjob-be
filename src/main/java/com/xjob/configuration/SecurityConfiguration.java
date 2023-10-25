package com.xjob.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.xjob.filter.Filter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private Filter filter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests()
				.antMatchers("/job/post-job").hasRole("CLIENT")
				.antMatchers("/proposal/post-proposal", "/user/update-freelancer-info",
						"/user/update-freelancer-skill", "/user/update-freelancer-experience",
						"/user//delete-freelancer-experience").hasRole("FREELANCER")
//				.antMatchers("/user/accounts", "/user/toggle-account").hasRole("ADMIN")
				.antMatchers("/user/verify-email", "/user/update-verify-code",
						"/job/job-by-author","/job/best-matchs","/notification-list","/user/freelancer-info","/user/client-info").authenticated()
				.antMatchers("/**").permitAll();
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		http.cors();
	}

}
