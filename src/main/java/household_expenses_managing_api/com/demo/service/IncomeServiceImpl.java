package household_expenses_managing_api.com.demo.service;

import household_expenses_managing_api.com.demo.entity.Income;
import household_expenses_managing_api.com.demo.exception.ResourceNotFoundException;
import household_expenses_managing_api.com.demo.mapper.IncomeMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService {
    private final IncomeMapper incomeMapper;

    public IncomeServiceImpl(IncomeMapper incomeMapper) {
        this.incomeMapper = incomeMapper;
    }

    @Override
    public List<Income> getAllIncome() {
        return incomeMapper.getAllIncome();
    }

    @Override
    public void addIncome(Income income) {
        Timestamp now = Timestamp.from(Instant.now());
        income.setCreatedAt(now);
        income.setUpdatedAt(now);

        incomeMapper.addIncome(income);
    }

    @Override
    public void updateIncome(Income income) {
        int id = income.getId();
        incomeMapper.getIncomeById(id).orElseThrow(() -> new ResourceNotFoundException("Income ID:" + id + "doesn't exist"));

        Timestamp now = Timestamp.from(Instant.now());
        income.setUpdatedAt(now);
        incomeMapper.updateIncome(income);
    }

    @Override
    public Income getIncomeById(int id) {
        Optional<Income> incomeOptional = incomeMapper.getIncomeById(id);

        Income income = incomeOptional.orElseThrow(() -> new ResourceNotFoundException("Income ID:" + id + "doesn't exist"));

        return income;
    }
}
