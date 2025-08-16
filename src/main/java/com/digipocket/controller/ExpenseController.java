package com.digipocket.controller;

import com.digipocket.dto.ExpenseRequest;
import com.digipocket.model.Expense;
import com.digipocket.model.User;
import com.digipocket.repository.UserRepository;
import com.digipocket.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
	private final ExpenseService expenseService;
	private final UserRepository userRepository;

	public ExpenseController(ExpenseService expenseService, UserRepository userRepository) {
		this.expenseService = expenseService;
		this.userRepository = userRepository;
	}

	private User currentUser(Authentication auth) {
		return userRepository.findByEmail(auth.getName()).orElseThrow();
	}

	@GetMapping
	public ResponseEntity<List<Expense>> list(Authentication auth, @RequestParam(required = false) String category,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
			@RequestParam(required = false, defaultValue = "desc") String sort) {
		return ResponseEntity.ok(expenseService.list(currentUser(auth), category, from, to, sort));
	}

	@PostMapping
	public ResponseEntity<Expense> create(Authentication auth, @Valid @RequestBody ExpenseRequest req) {
		return ResponseEntity.ok(expenseService.add(currentUser(auth), req));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Expense> update(Authentication auth, @PathVariable Long id,
			@Valid @RequestBody ExpenseRequest req) {
		return ResponseEntity.ok(expenseService.update(currentUser(auth), id, req));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(Authentication auth, @PathVariable Long id) {
		expenseService.delete(currentUser(auth), id);
		return ResponseEntity.noContent().build();
	}
}
