package com.sunbeam.tikito.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunbeam.tikito.dto.AuthRequestDto;
import com.sunbeam.tikito.dto.AuthResponseDto;
import com.sunbeam.tikito.serviceimpl.AuthServiceImpl;
import com.sunbeam.tikito.utils.Resp;

@RestController
@RequestMapping("/tikito/auth")
public class AuthController 
{
	private AuthServiceImpl ser;
	
	@PostMapping("/login")
	public Resp<?> login(@RequestBody AuthRequestDto req)
	{
		AuthResponseDto res = ser.login(req);
		return Resp.success(res);
	}
}
