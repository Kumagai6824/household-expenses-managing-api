package household_expenses_managing_api.com.demo.controller;

import household_expenses_managing_api.com.demo.entity.Income;
import household_expenses_managing_api.com.demo.response.ApiResponse;
import household_expenses_managing_api.com.demo.service.IncomeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
public class IncomeController {
    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping("/income")
    public ApiResponse<List<Income>> getAllIncome() {
        List<Income> incomes=incomeService.getAllIncome();
        return new ApiResponse<>(incomes,"Income records fetched successfully");
    }

}


