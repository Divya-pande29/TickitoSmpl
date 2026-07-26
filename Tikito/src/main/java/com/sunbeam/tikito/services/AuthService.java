package com.sunbeam.tikito.services;

import com.sunbeam.tikito.dto.AuthRequestDto;
import com.sunbeam.tikito.dto.AuthResponseDto;

public interface AuthService
{
	AuthResponseDto authenticateUser(AuthRequestDto request);
}
