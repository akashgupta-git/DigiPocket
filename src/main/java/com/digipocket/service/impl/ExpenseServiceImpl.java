package com.digipocket.service.impl;

import com.digipocket.dto.ExpenseRequest;
import com.digipocket.exception.ApiException;
import com.digipocket.model.Expense;
import com.digipocket.model.User;
import com.digipocket.repository.ExpenseRepository;
import com.digipocket.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {
	private final ExpenseRepository expenseRepository;

	public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
		this.expenseRepository = expenseRepository;
	}

	@Override
	public Expense add(User user, ExpenseRequest req) {
		Expense e = new Expense();
		e.setTitle(req.getTitle());
		e.setCategory(req.getCategory());
		e.setAmount(req.getAmount());
		e.setDate(req.getDate());
		e.setUser(user);
		return expenseRepository.save(e);
	}

	@Override
	public List<Expense> list(User user, String category, LocalDate from, LocalDate to, String sort) {
		List<Expense> list;
		if (category != null && from != null && to != null)
			list = expenseRepository.findByUserAndCategoryAndDateBetween(user, category, from, to);
		else if (category != null)
			list = expenseRepository.findByUserAndCategory(user, category);
		else if (from != null && to != null)
			list = expenseRepository.findByUserAndDateBetween(user, from, to);
		else
			list = expenseRepository.findByUser(user);
		if ("desc".equalsIgnoreCase(sort))
			list.sort(Comparator.comparing(Expense::getDate).reversed());
		else if ("asc".equalsIgnoreCase(sort))
			list.sort(Comparator.comparing(Expense::getDate));
		return list;
	}

	@Override
	public Expense update(User user, Long id, ExpenseRequest req) {
		Expense e = expenseRepository.findById(id)
				.orElseThrow(() -> new ApiException("Expense not found", HttpStatus.NOT_FOUND));
		if (!e.getUser().getId().equals(user.getId()))
			throw new ApiException("Forbidden", HttpStatus.FORBIDDEN);
		e.setTitle(req.getTitle());
		e.setCategory(req.getCategory());
		e.setAmount(req.getAmount());
		e.setDate(req.getDate());
		return expenseRepository.save(e);
	}

	@Override
	public void delete(User user, Long id) {
		Expense e = expenseRepository.findById(id)
				.orElseThrow(() -> new ApiException("Expense not found", HttpStatus.NOT_FOUND));
		if (!e.getUser().getId().equals(user.getId()))
			throw new ApiException("Forbidden", HttpStatus.FORBIDDEN);
		expenseRepository.delete(e);
	}
}
