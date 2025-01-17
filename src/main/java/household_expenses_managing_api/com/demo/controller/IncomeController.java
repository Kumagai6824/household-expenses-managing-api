package household_expenses_managing_api.com.demo.controller;

import household_expenses_managing_api.com.demo.entity.Income;
import household_expenses_managing_api.com.demo.service.IncomeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Validated
public class IncomeController {
    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping("/income")
    public List<Income> getAllIncome() {
        return incomeService.getAllIncome();
    }

}


