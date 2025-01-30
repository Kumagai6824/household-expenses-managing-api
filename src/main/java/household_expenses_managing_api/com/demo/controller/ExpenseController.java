package household_expenses_managing_api.com.demo.controller;

import household_expenses_managing_api.com.demo.entity.Expense;
import household_expenses_managing_api.com.demo.form.CreateExpenseForm;
import household_expenses_managing_api.com.demo.form.UpdateExpenseForm;
import household_expenses_managing_api.com.demo.response.ApiResponse;
import household_expenses_managing_api.com.demo.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expense")
    public ApiResponse<List<Expense>> getAllExpense() {
        List<Expense> expenses = expenseService.getAllExpense();
        return new ApiResponse<>(expenses, "Expense records fetched successfully");
    }

    @PostMapping("/expense")
    public ResponseEntity<Map<String, String>> addExpense(@RequestBody @Validated CreateExpenseForm form, UriComponentsBuilder uriComponentsBuilder) {
        Expense expenseEntity = form.convertToExpenseEntity();
        expenseService.addExpense(expenseEntity);
        int id = expenseEntity.getId();
        URI url = uriComponentsBuilder.path("/expense" + id).build().toUri();
        return ResponseEntity.created(url).
                body(Map.of("message", "Expense added successfully"));
    }

    @PatchMapping("/expense/{id}")
    public ResponseEntity<Map<String, String>> updateExpense(@PathVariable int id, @RequestBody @Validated UpdateExpenseForm form) throws Exception {
        form.setId(id);
        Expense expenseEntity = form.convertToExpenseEntity();
        expenseService.updateExpense(expenseEntity);
        return ResponseEntity.ok(Map.of("message", "Expense updated successfully"));
    }

    @GetMapping("/expense/{id}")
    public ApiResponse<Expense> getExpenseById(@PathVariable int id) {
        Expense expense = expenseService.getExpenseById(id);
        return new ApiResponse<>(expense, "Expense record fetched successfully");
    }

    @DeleteMapping("/expense/{id}")
    public ResponseEntity<Map<String, String>> deleteExpense(@PathVariable int id) throws Exception {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok(Map.of("message", "Expense deleted successfully"));
    }

    @GetMapping("/expense/filter")
    public List<Expense> getExpenseByYearAndMonth(
            @RequestParam int year,
            @RequestParam int month) {
        return expenseService.getExpenseByYearAndMonth(year, month);
    }
}


