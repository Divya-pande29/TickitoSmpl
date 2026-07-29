package com.sunbeam.tikito.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
import com.sunbeam.tikito.daos.UserDao;
import com.sunbeam.tikito.dto.UserDto;
import com.sunbeam.tikito.entity.UserEntity;
import com.sunbeam.tikito.services.UserService;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto register(UserDto dto) {
    	
    	

        if (userDao.findByEmail(dto.getEmail()).isPresent())
            throw new RuntimeException("Email already exists");

        if (userDao.findByPhone(dto.getPhone()).isPresent())
            throw new RuntimeException("Phone already exists");

        UserEntity user = modelMapper.map(dto, UserEntity.class);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("ROLE_USER");

        UserEntity savedUser = userDao.save(user);

        UserDto response = modelMapper.map(savedUser, UserDto.class);

        response.setPassword(null);
        response.setOldPassword(null);
        response.setNewPassword(null);

        return response;
    }

    @Override
    public UserDto getProfile(Long userId) {

        UserEntity user = userDao.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        UserDto response = modelMapper.map(user, UserDto.class);

        response.setPassword(null);
        response.setOldPassword(null);
        response.setNewPassword(null);

        return response;
    }

    @Override
    public String updatePassword(Long userId, UserDto dto) {

        UserEntity user = userDao.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        if (!passwordEncoder.matches(dto.getOldPassword(),
                                     user.getPassword()))
            throw new RuntimeException("Old Password is Incorrect");

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        userDao.save(user);

        return "Password Updated Successfully";
    }

    @Override
    public String forgotPassword(UserDto dto) {

        UserEntity user = userDao.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        userDao.save(user);

        return "Password Reset Successfully";
    }

    @Override
    public String deleteAccount(Long userId) {

        UserEntity user = userDao.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        userDao.delete(user);

        return "Account Deleted Successfully";
    }
}





