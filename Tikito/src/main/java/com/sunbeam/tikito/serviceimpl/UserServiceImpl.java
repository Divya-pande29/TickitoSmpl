package com.sunbeam.tikito.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sunbeam.tikito.daos.UserDao;
import com.sunbeam.tikito.dto.UserDto;
import com.sunbeam.tikito.entity.UserEntity;
import com.sunbeam.tikito.services.UserService;

import jakarta.transaction.Transactional;
	
@Transactional	
@Service
	public class UserServiceImpl implements UserService{
	    private UserDao userDao;
	    private ModelMapper modelMapper;
	    
	    @Autowired
	    public UserServiceImpl(UserDao userDao, ModelMapper modelMapper) {
			this.userDao = userDao;
			this.modelMapper = modelMapper;
		}

		@Override
		public UserDto register(UserDto dto) {
			 if (userDao.findByEmail(dto.getEmail()).isPresent()) {
				 throw new RuntimeException("Email already exists");
			    }

			    if (userDao.findByPhone(dto.getPhone()).isPresent()) {
			        throw new RuntimeException("Phone already exists");
			    }

			    UserEntity user =
			            modelMapper.map(dto, UserEntity.class);

			    user.setRole("USER");

			    UserEntity savedUser =
			            userDao.save(user);

			    UserDto response =
			            modelMapper.map(savedUser,
			                            UserDto.class);

			    response.setPassword(null);
			    response.setOldPassword(null);
			    response.setNewPassword(null);

			    return response;
			
		}

		@Override
		public UserDto login(UserDto dto) {
			  UserEntity user =
		                userDao.findByEmail(dto.getEmail())
		                       .orElseThrow(() ->
		                               new RuntimeException(
		                                       "Invalid Email"));

		        if (!user.getPassword()
		                 .equals(dto.getPassword())) {
		            throw new RuntimeException(
		                    "Invalid Password");
		        }

		        UserDto response =
		                modelMapper.map(user, UserDto.class);

		        response.setPassword(null);
		        response.setOldPassword(null);
		        response.setNewPassword(null);

		        return response;
		
		}

		@Override
		public UserDto getProfile(Long userId) {
			  UserEntity user =
		                userDao.findById(userId)
		                       .orElseThrow(() ->
		                               new RuntimeException(
		                                       "User Not Found"));

			  UserDto response =
				        modelMapper.map(user, UserDto.class);

				response.setPassword(null);
				response.setOldPassword(null);
				response.setNewPassword(null);

				return response;
	
		}

		@Override
		public String updatePassword(UserDto dto) {
			 UserEntity user =
		                userDao.findById(dto.getUserId())
		                       .orElseThrow(() ->
		                               new RuntimeException(
		                                       "User Not Found"));

		        if (!user.getPassword()
		                 .equals(dto.getOldPassword())) {
		            throw new RuntimeException(
		                    "Old Password is Incorrect");
		        }

		        user.setPassword(dto.getNewPassword());

		        userDao.save(user);

		        return "Password Updated Successfully";
		}

		@Override
		public String forgotPassword(UserDto dto) {

	        UserEntity user =
	                userDao.findByEmail(dto.getEmail())
	                       .orElseThrow(() ->
	                               new RuntimeException(
	                                       "User Not Found"));

	        user.setPassword(dto.getNewPassword());

	        userDao.save(user);

	        return "Password Reset Successfully";
		
		}

		@Override
		public String deleteAccount(Long userId) {
		      UserEntity user =
		                userDao.findById(userId)
		                       .orElseThrow(() ->
		                               new RuntimeException(
		                                       "User Not Found"));

		        userDao.delete(user);

		        return "Account Deleted Successfully";
		    }
			
}

//		@Override
//		public UserRegistrationDto register(UserRegistrationDto dto) {
//	        if (userDao.findByEmail(dto.getEmail()).isPresent()) {
//	            throw new RuntimeException("Email already exists");
//	        }
//	        if (userDao.findByPhone(dto.getPhone()).isPresent()) {
//	            throw new RuntimeException("Phone number already exists");
//	        }
//	        UserEntity user = modelMapper.map(dto, UserEntity.class);
//	        user.setRole("USER");
//	        UserEntity dbUser = userDao.save(user);  
//	        dto.setUserId(dbUser.getUserId());
//	        return dto;
//	        
//		}


