package com.sunbeam.tikito.controllers;
		
		import org.springframework.web.bind.annotation.DeleteMapping;
		import org.springframework.web.bind.annotation.GetMapping;
		import org.springframework.web.bind.annotation.PathVariable;
		import org.springframework.web.bind.annotation.PostMapping;
		import org.springframework.web.bind.annotation.PutMapping;
		import org.springframework.web.bind.annotation.RequestBody;
		import org.springframework.web.bind.annotation.RequestMapping;
		import org.springframework.web.bind.annotation.RestController;
		import com.sunbeam.tikito.dto.UserDto;
import com.sunbeam.tikito.serviceimpl.UserServiceImpl;
import com.sunbeam.tikito.utils.Resp;

		@RequestMapping("/users")
		@RestController
		public class UserController {

		    private UserServiceImpl userService;

		    public UserController(UserServiceImpl userService) {
		        this.userService = userService;
		    }

		    // Registration
		    @PostMapping
		    public Resp<?> registerUser( @RequestBody UserDto dto) {
		    	dto = userService.register(dto);
		        return Resp.success(dto);
		    }

		    // Login
		    @PostMapping("/login")
		    public Resp<?> loginUser(@RequestBody UserDto dto) {
		        dto = userService.login(dto);
		        return Resp.success(dto);
		    }

		    // Get Profile
		    @GetMapping("/{userId}")
		    public Resp<?> getProfile(@PathVariable Long userId) {
		        UserDto dto = userService.getProfile(userId);
		        return Resp.success(dto);
		    }

		    // Update Password
		    @PutMapping("/update-password")
		    public Resp<?> updatePassword(@RequestBody UserDto dto) {
		        String msg = userService.updatePassword(dto);
		        return Resp.success(msg);
		    }

		    // Forgot Password
		    @PutMapping("/forgot-password")
		    public Resp<?> forgotPassword(@RequestBody UserDto dto) {
		        String msg = userService.forgotPassword(dto);
		        return Resp.success(msg);
		    }

		    // Delete Account
		    @DeleteMapping("/{userId}")
		    public Resp<?> deleteAccount(@PathVariable Long userId) {
		        String msg = userService.deleteAccount(userId);
		        return Resp.success(msg);
		    }

	   

}
