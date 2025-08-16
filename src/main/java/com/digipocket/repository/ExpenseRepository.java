package com.digipocket.repository;

import com.digipocket.model.Expense;
import com.digipocket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	List<Expense> findByUser(User user);

	List<Expense> findByUserAndCategory(User user, String category);

	List<Expense> findByUserAndDateBetween(User user, LocalDate from, LocalDate to);

	List<Expense> findByUserAndCategoryAndDateBetween(User user, String category, LocalDate from, LocalDate to);
}
