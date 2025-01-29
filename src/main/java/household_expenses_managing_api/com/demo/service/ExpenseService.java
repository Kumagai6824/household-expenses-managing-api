package household_expenses_managing_api.com.demo.service;

import household_expenses_managing_api.com.demo.entity.Expense;

import java.util.List;

public interface ExpenseService {
    List<Expense> getAllExpense();

    void addExpense(Expense expense);

    void updateExpense(Expense expense);

    Expense getExpenseById(int id);

    void deleteExpense(int id);
}
