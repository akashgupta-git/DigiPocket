package com.digipocket.service;

import com.digipocket.dto.ExpenseRequest;
import com.digipocket.model.Expense;
import com.digipocket.model.User;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
	Expense add(User user, ExpenseRequest req);

	List<Expense> list(User user, String category, LocalDate from, LocalDate to, String sort);

	Expense update(User user, Long id, ExpenseRequest req);

	void delete(User user, Long id);
}
