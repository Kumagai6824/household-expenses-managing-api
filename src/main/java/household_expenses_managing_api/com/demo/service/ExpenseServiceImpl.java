package household_expenses_managing_api.com.demo.service;

import household_expenses_managing_api.com.demo.entity.Expense;
import household_expenses_managing_api.com.demo.exception.ResourceNotFoundException;
import household_expenses_managing_api.com.demo.mapper.ExpenseMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseMapper expenseMapper;

    public ExpenseServiceImpl(ExpenseMapper expenseMapper) {
        this.expenseMapper = expenseMapper;
    }

    @Override
    public List<Expense> getAllExpense() {
        return expenseMapper.getAllExpense();
    }

    @Override
    public void addExpense(Expense expense) {
        Timestamp now = Timestamp.from(Instant.now());
        expense.setCreatedAt(now);
        expense.setUpdatedAt(now);

        expenseMapper.addExpense(expense);
    }

    @Override
    public void updateExpense(Expense expense) {
        int id = expense.getId();
        expenseMapper.getExpenseById(id).orElseThrow(() -> new ResourceNotFoundException("Expense ID: " + id + " doesn't exist"));

        Timestamp now = Timestamp.from(Instant.now());
        expense.setUpdatedAt(now);
        expenseMapper.updateExpense(expense);
    }

    @Override
    public Expense getExpenseById(int id) {
        Optional<Expense> expenseOptional = expenseMapper.getExpenseById(id);

        Expense expense = expenseOptional.orElseThrow(() -> new ResourceNotFoundException("Expense ID: " + id + " doesn't exist"));

        return expense;
    }

    @Override
    public void deleteExpense(int id) {
        expenseMapper.getExpenseById(id).orElseThrow(() -> new ResourceNotFoundException("Expense ID: " + id + " doesn't exist"));
        expenseMapper.deleteExpense(id);
    }

    @Override
    public List<Expense> getExpenseByYearAndMonth(int year, int month) {
        return expenseMapper.getAllExpense().stream()
                .filter(expense -> expense.getUsedDate().getYear() == year && expense.getUsedDate().getMonthValue() == month)
                .collect(Collectors.toList());
    }
}
