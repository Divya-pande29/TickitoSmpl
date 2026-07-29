package com.sunbeam.tikito.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
	private final UserDetailsService userDetailsService;
	private final JwtFilter jwtFilter;
	
	public SecurityConfig(UserDetailsService userDetailsService, JwtFilter jwtFilter)
	{
		this.userDetailsService = userDetailsService;
		this.jwtFilter = jwtFilter;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception
	{
		AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		
		return authManagerBuilder.build();
	}
	
	@Bean
	SecurityFilterChain authorizeRequests(HttpSecurity http) throws Exception
	{
		http.csrf(csrf -> csrf.disable())
		    .cors(cors -> { })
			.authorizeHttpRequests(requests -> requests
								   .requestMatchers("/tikito/auth/**","/tikito/user/register", "/tikito/user/forgot-password").permitAll()
								   .requestMatchers("/tikito/admin/register").permitAll()
								   .requestMatchers("/tikito/booking/user/**").hasRole("USER")
								   .requestMatchers("/tikito/booking/admin/**").hasRole("ADMIN")
								   .requestMatchers("/tikito/user/**").hasRole("USER")
								   .requestMatchers(HttpMethod.POST, "/tikito/events/**").hasRole("ADMIN")
								   .requestMatchers(HttpMethod.PUT, "/tikito/events/**").hasRole("ADMIN")
								   .requestMatchers(HttpMethod.DELETE, "/tikito/events/**").hasRole("ADMIN")
								   .requestMatchers(HttpMethod.GET, "/tikito/events/**").permitAll()
								   .requestMatchers("/tikito/venue", "/tikito/venue/*", "/tikito/venue/name/*", "/tikito/venue/address/*").permitAll()
								   .requestMatchers("/tikito/venue/admin/**").hasRole("ADMIN")
								   .requestMatchers("/tikito/shows", "/tikito/shows/*", "/tikito/shows/event/*", "/tikito/shows/date/*", "/tikito/shows/time/*").permitAll()
								   .requestMatchers("/tikito/shows/admin/**").hasRole("ADMIN")
								   .requestMatchers("/posters/**").permitAll()
								   .anyRequest().authenticated())
								   .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
								   .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		    
		
		return http.build();
	}
}
