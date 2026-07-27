package com.sunbeam.tikito.serviceimpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sunbeam.tikito.daos.UserDao;
import com.sunbeam.tikito.entity.UserEntity;

public class CustomUserDetailsService implements UserDetailsService{

	private UserDao userdao;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userdao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found"));
		if (user == null)
			throw new UsernameNotFoundException("user not found");
		return user;
	}
	

}
