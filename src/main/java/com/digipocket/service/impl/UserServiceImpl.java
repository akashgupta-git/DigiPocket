package com.digipocket.service.impl;

import com.digipocket.dto.AuthRequest;
import com.digipocket.dto.AuthResponse;
import com.digipocket.dto.RegisterRequest;
import com.digipocket.exception.ApiException;
import com.digipocket.model.User;
import com.digipocket.repository.UserRepository;
import com.digipocket.security.JwtUtil;
import com.digipocket.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder encoder;
	private final AuthenticationManager authManager;
	private final JwtUtil jwtUtil;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder, AuthenticationManager authManager,
			JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public void register(RegisterRequest req) {
		if (userRepository.existsByEmail(req.getEmail()))
			throw new ApiException("Email already in use", HttpStatus.BAD_REQUEST);
		User u = new User();
		u.setUsername(req.getUsername());
		u.setEmail(req.getEmail());
		u.setPassword(encoder.encode(req.getPassword()));
		userRepository.save(u);
	}

	@Override
	public AuthResponse login(AuthRequest req) {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
		} catch (BadCredentialsException ex) {
			throw new ApiException("Invalid credentials", HttpStatus.UNAUTHORIZED);
		}
		return new AuthResponse(jwtUtil.generateToken(req.getEmail()));
	}
}
