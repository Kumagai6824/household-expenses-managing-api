package household_expenses_managing_api.com.demo.controller;

import household_expenses_managing_api.com.demo.entity.Income;
import household_expenses_managing_api.com.demo.form.CreateIncomeForm;
import household_expenses_managing_api.com.demo.form.UpdateIncomeForm;
import household_expenses_managing_api.com.demo.response.ApiResponse;
import household_expenses_managing_api.com.demo.service.IncomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
    public ApiResponse<List<Income>> getAllIncome() {
        List<Income> incomes = incomeService.getAllIncome();
        return new ApiResponse<>(incomes, "Income records fetched successfully");
    }

    @PostMapping("/income")
    public ResponseEntity<Map<String, String>> addIncome(@RequestBody @Validated CreateIncomeForm form, UriComponentsBuilder uriComponentsBuilder) {
        Income incomeEntity = form.convertToIncomeEntity();
        incomeService.addIncome(incomeEntity);
        int id = incomeEntity.getId();
        URI url = uriComponentsBuilder.path("/income" + id).build().toUri();
        return ResponseEntity.created(url).
                body(Map.of("message", "Income added successfully"));
    }

    @PatchMapping("/income/{id}")
    public ResponseEntity<Map<String, String>> updateIncome(@PathVariable int id, @RequestBody @Validated UpdateIncomeForm form) throws Exception {
        form.setId(id);
        Income incomeEntity = form.convertToIncomeEntity();
        incomeService.updateIncome(incomeEntity);
        return ResponseEntity.ok(Map.of("message", "Income updated successfully"));
    }
}


