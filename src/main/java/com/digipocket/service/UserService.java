package com.digipocket.service;

import com.digipocket.dto.AuthRequest;
import com.digipocket.dto.AuthResponse;
import com.digipocket.dto.RegisterRequest;

public interface UserService {
	void register(RegisterRequest req);

	AuthResponse login(AuthRequest req);
}
