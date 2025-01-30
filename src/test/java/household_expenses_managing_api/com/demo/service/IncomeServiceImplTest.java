package household_expenses_managing_api.com.demo.service;

import household_expenses_managing_api.com.demo.entity.Income;
import household_expenses_managing_api.com.demo.exception.ResourceNotFoundException;
import household_expenses_managing_api.com.demo.mapper.IncomeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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
        int id = 1;
        Optional<Income> income = Optional.of(new Income(id, Income.Type.ACTUAL, "salary", 5000, LocalDate.of(2024, 1, 10), null, null));
        Income updateIncome = new Income(id, Income.Type.PROJECTED, "UpdatedSalary", 55555, LocalDate.of(2025, 10, 11), null, null);

        doReturn(income).when(incomeMapper).getIncomeById(id);
        doNothing().when(incomeMapper).updateIncome(updateIncome);

        incomeServiceImpl.updateIncome(updateIncome);
        verify(incomeMapper, times(1)).updateIncome(updateIncome);

    }

    @Test
    public void updateIncomeで存在しないidのとき例外を返すこと() {
        int id = 0;
        Income updateIncome = new Income(id, Income.Type.PROJECTED, "UpdatedSalary", 55555, LocalDate.of(2025, 10, 11), null, null);
        doReturn(Optional.empty()).when(incomeMapper).getIncomeById(id);

        assertThatThrownBy(() -> incomeServiceImpl.updateIncome(updateIncome))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Income ID: " + id + " doesn't exist");

    }

    @Test
    public void getIncomeByIdで該当incomeを取得できること() {
        int id = 1;
        Optional<Income> income = Optional.of(new Income(id, Income.Type.ACTUAL, "salary", 5000, LocalDate.of(2024, 1, 10), null, null));
        doReturn(income).when(incomeMapper).getIncomeById(id);

        Income actualIncome = incomeServiceImpl.getIncomeById(id);
        verify(incomeMapper, times(1)).getIncomeById(id);
    }

    @Test
    public void getIncomeByIdで存在しないid指定時に例外を返すこと() {
        int id = 0;
        doReturn(Optional.empty()).when(incomeMapper).getIncomeById(id);
        assertThatThrownBy(() -> incomeServiceImpl.getIncomeById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Income ID: " + id + " doesn't exist");
    }

    @Test
    public void deleteIncomeで削除できること() {
        int id = 1;
        Optional<Income> income = Optional.of(new Income(id, Income.Type.ACTUAL, "salary", 5000, LocalDate.of(2024, 1, 10), null, null));
        doReturn(income).when(incomeMapper).getIncomeById(id);
        doNothing().when(incomeMapper).deleteIncome(id);
        incomeServiceImpl.deleteIncome(id);
        verify(incomeMapper, times(1)).deleteIncome(id);
    }

    @Test
    public void deleteIncomeで存在しないidのとき例外を返すこと() {
        int id = 0;
        doReturn(Optional.empty()).when(incomeMapper).getIncomeById(id);
        assertThatThrownBy(() -> incomeServiceImpl.deleteIncome(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Income ID: " + id + " doesn't exist");
    }

    @Test
    public void getIncomeByYearAndMonthでフィルターできること() {
        int year = 2024;
        int month = 1;

        List<Income> incomes = List.of(new Income(1, Income.Type.ACTUAL, "salary", 5000, LocalDate.of(2024, 1, 10), null, null), new Income(2, Income.Type.PROJECTED, "Bonus", 200000, LocalDate.of(2024, 1, 25), null, null), new Income(3, Income.Type.PROJECTED, "Salary", 100000, LocalDate.of(2024, 2, 10), null, null));
        doReturn(incomes).when(incomeMapper).getAllIncome();

        List<Income> expectedIncomes = List.of(new Income(1, Income.Type.ACTUAL, "salary", 5000, LocalDate.of(2024, 1, 10), null, null), new Income(2, Income.Type.PROJECTED, "Bonus", 200000, LocalDate.of(2024, 1, 25), null, null));

        List<Income> actualIncomes = incomeServiceImpl.getIncomeByYearAndMonth(year, month);
        verify(incomeMapper, times(1)).getAllIncome();
        assertThat(actualIncomes).isEqualTo(expectedIncomes);
    }

}
