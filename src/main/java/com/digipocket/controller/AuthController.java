package com.digipocket.controller;

import com.digipocket.dto.AuthRequest;
import com.digipocket.dto.AuthResponse;
import com.digipocket.dto.RegisterRequest;
import com.digipocket.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest req) {
		userService.register(req);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest req) {
		return ResponseEntity.ok(userService.login(req));
	}
}
