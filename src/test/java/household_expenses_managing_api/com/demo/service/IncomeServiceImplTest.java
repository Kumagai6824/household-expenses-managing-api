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

import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class IncomeServiceImplTest {
    @InjectMocks
    IncomeServiceImpl incomeServiceImpl;
    @Mock
    IncomeMapper incomeMapper;

    @Test
    public void 全incomeを取得できること(){
        List<Income> expectdIncome=List.of(new Income(1, Income.Type.ACTUAL,"salary",5000, LocalDate.of(2024,1,10),null,null));
        doReturn(expectdIncome).when(incomeMapper).getAllIncome();

        List<Income> actualIncome= incomeServiceImpl.getAllIncome();
        assertThat(actualIncome).isEqualTo(expectdIncome);
    }

}
