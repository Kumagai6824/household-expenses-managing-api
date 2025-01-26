package household_expenses_managing_api.com.demo.service;

import household_expenses_managing_api.com.demo.entity.Income;
import household_expenses_managing_api.com.demo.mapper.IncomeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncomeServiceImplTest {
    @InjectMocks
    IncomeServiceImpl incomeServiceImpl;
    @Mock
    IncomeMapper incomeMapper;

    @Test
    public void 全incomeを取得できること() {
        List<Income> expectdIncome = List.of(new Income(1, Income.Type.ACTUAL, "salary", 5000, LocalDate.of(2024, 1, 10), null, null));
        doReturn(expectdIncome).when(incomeMapper).getAllIncome();

        List<Income> actualIncome = incomeServiceImpl.getAllIncome();
        assertThat(actualIncome).isEqualTo(expectdIncome);
    }

    @Test
    public void addIncomeで登録できること() {
        Income income = new Income(Income.Type.ACTUAL, "salary", 5000, LocalDate.of(2024, 1, 10));

        doNothing().when(incomeMapper).addIncome(income);
        incomeServiceImpl.addIncome(income);
        verify(incomeMapper, times(1)).addIncome(income);
    }

    @Test
    public void updateIncomeで更新できること() {
        Income updateIncome = new Income(1, Income.Type.PROJECTED, "UpdatedSalary", 55555, LocalDate.of(2025, 10, 11), null, null);
        doNothing().when(incomeMapper).updateIncome(updateIncome);
        incomeServiceImpl.updateIncome(updateIncome);
        verify(incomeMapper, times(1)).updateIncome(updateIncome);

    }

}
