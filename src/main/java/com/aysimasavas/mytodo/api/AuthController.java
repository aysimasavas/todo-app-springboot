package com.aysimasavas.mytodo.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aysimasavas.mytodo.entities.User;
import com.aysimasavas.mytodo.request.UserRequest;
import com.aysimasavas.mytodo.response.AuthResponse;
import com.aysimasavas.mytodo.security.JwtTokenProvider;
import com.aysimasavas.mytodo.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	private AuthenticationManager authenticationManager;

	private JwtTokenProvider jwtTokenProvider;

	private UserService userService;

	private PasswordEncoder passwordEncoder;

	
	public AuthController(AuthenticationManager authenticationManager, UserService userService,
			PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@PostMapping("/login")
	public AuthResponse login(@RequestBody UserRequest loginRequest) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				loginRequest.getUserName(), loginRequest.getPassword());
		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = jwtTokenProvider.generateJwtToken(auth);
		User user = userService.getOneUserByUserName(loginRequest.getUserName());
		AuthResponse authresponse = new AuthResponse();
		authresponse.setMessage("Bearer: " + jwtToken);
		authresponse.setUserId(user.getId());
		return authresponse;
	}


	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody UserRequest registerRequest) {
		AuthResponse authresponse = new AuthResponse();
		if (userService.getOneUserByUserName(registerRequest.getUserName()) != null) {
			authresponse.setMessage("Username already in use.");
			return new ResponseEntity<>(authresponse, HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setUserName(registerRequest.getUserName());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		userService.saveOneUser(user);

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				registerRequest.getUserName(), registerRequest.getPassword());
		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = jwtTokenProvider.generateJwtToken(auth);
		authresponse.setMessage("successfull.");
		return new ResponseEntity<>(authresponse, HttpStatus.CREATED);
	}

}
