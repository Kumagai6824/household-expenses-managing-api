package household_expenses_managing_api.com.demo.service;

import household_expenses_managing_api.com.demo.entity.Income;
import household_expenses_managing_api.com.demo.mapper.IncomeMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

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
    public void addIncome(Income income){
        Timestamp now=Timestamp.from(Instant.now());
        income.setCreatedAt(now);
        income.setUpdatedAt(now);

        incomeMapper.addIncome(income);
    }
}
