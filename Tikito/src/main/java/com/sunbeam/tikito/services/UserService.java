package com.sunbeam.tikito.services;

import com.sunbeam.tikito.dto.UserDto;

public interface UserService {
	UserDto register(UserDto dto);
//	UserDto login(UserDto dto);
	UserDto getProfile(Long userId);
	String updatePassword(UserDto dto);
	String forgotPassword(UserDto dto);
	String deleteAccount(Long userId);
}	
//	String forgotPassword(String email,String newPassword);


