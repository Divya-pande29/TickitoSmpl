package com.sunbeam.tikito.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig
{
	private UserDetailsService userDetailsService;
	private JwtFilter jwtFilter;
	
	public SecurityConfig(UserDetailsService userDetailsService, JwtFilter jwtFilter)
	{
		this.userDetailsService = userDetailsService;
		this.jwtFilter = jwtFilter;
	}
	
	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception
	{
		AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authManagerBuilder.userDetailsService(userDetailsService);
		
		return authManagerBuilder.build();
	}
	
	@Bean
	SecurityFilterChain authorizeRequests(HttpSecurity http) throws Exception
	{
		http.csrf(csrf -> csrf.disable())
		    .cors(cors -> { })
			.authorizeHttpRequests(requests -> requests
								   .requestMatchers("/tikito/auth/**","tikito/user/register", "/tikito/user/forgot-password").permitAll()
								   .requestMatchers("/tikito/admin/register").hasRole("ADMIN")
								   .requestMatchers("/tikito/booking/user/**").hasRole("USER")
								   .requestMatchers("/tikito/booking/admin/**").hasRole("ADMIN")
								   .requestMatchers("/tikito/user/**").hasRole("USER")
								   .requestMatchers("/tikito/events/**").hasRole("ADMIN")
								   .requestMatchers("").hasRole("ADMIN")//VENUE, SHOW
								   .requestMatchers("").hasRole("USER")//SHOW
								   .anyRequest().authenticated())
								   .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
								   .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		    
		
		return http.build();
	}
}
