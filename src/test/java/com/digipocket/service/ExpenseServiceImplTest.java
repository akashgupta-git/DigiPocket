package com.digipocket.service;

import com.digipocket.dto.ExpenseRequest;
import com.digipocket.model.Expense;
import com.digipocket.model.User;
import com.digipocket.repository.ExpenseRepository;
import com.digipocket.service.impl.ExpenseServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class ExpenseServiceImplTest {
	@Test
	void add_shouldPersistExpense() {
		ExpenseRepository repo = Mockito.mock(ExpenseRepository.class);
		ExpenseServiceImpl service = new ExpenseServiceImpl(repo);
		User user = new User();
		user.setId(1L);
		ExpenseRequest req = new ExpenseRequest();
		req.setTitle("Dinner");
		req.setCategory("Food");
		req.setAmount(500.0);
		req.setDate(LocalDate.now());
		Mockito.when(repo.save(any(Expense.class))).thenAnswer(inv -> inv.getArgument(0));
		Expense saved = service.add(user, req);
		assertEquals("Dinner", saved.getTitle());
		assertEquals("Food", saved.getCategory());
	}
}
