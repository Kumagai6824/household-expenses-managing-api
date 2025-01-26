package household_expenses_managing_api.com.demo.service;

import household_expenses_managing_api.com.demo.entity.Income;

import java.util.List;

public interface IncomeService {
    List<Income> getAllIncome();

    void addIncome(Income income);

    void updateIncome(Income income);
}
