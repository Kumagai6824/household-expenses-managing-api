package household_expenses_managing_api.com.demo.service;

import household_expenses_managing_api.com.demo.entity.Expense;
import household_expenses_managing_api.com.demo.exception.ResourceNotFoundException;
import household_expenses_managing_api.com.demo.mapper.ExpenseMapper;
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
class ExpenseServiceImplTest {
    @InjectMocks
    ExpenseServiceImpl expenseServiceImpl;
    @Mock
    ExpenseMapper expenseMapper;

    @Test
    public void 全expenseを取得できること() {
        List<Expense> expectdExpense = List.of(new Expense(1, Expense.Type.ACTUAL, "food", 7000, LocalDate.of(2025, 1, 10), null, null));
        doReturn(expectdExpense).when(expenseMapper).getAllExpense();

        List<Expense> actualExpense = expenseServiceImpl.getAllExpense();
        assertThat(actualExpense).isEqualTo(expectdExpense);
    }

    @Test
    public void addExpenseで登録できること() {
        Expense expense = new Expense(Expense.Type.ACTUAL, "new food", 3000, LocalDate.of(2024, 1, 10));

        doNothing().when(expenseMapper).addExpense(expense);
        expenseServiceImpl.addExpense(expense);
        verify(expenseMapper, times(1)).addExpense(expense);
    }

    @Test
    public void updateExpenseで更新できること() {
        int id = 1;
        Optional<Expense> expense = Optional.of(new Expense(id, Expense.Type.ACTUAL, "food", 7000, LocalDate.of(2025, 1, 10), null, null));
        Expense updateExpense = new Expense(id, Expense.Type.PROJECTED, "UpdatedFood", 7777, LocalDate.of(2025, 10, 11), null, null);

        doReturn(expense).when(expenseMapper).getExpenseById(id);
        doNothing().when(expenseMapper).updateExpense(updateExpense);

        expenseServiceImpl.updateExpense(updateExpense);
        verify(expenseMapper, times(1)).updateExpense(updateExpense);

    }

    @Test
    public void updateExpenseで存在しないidのとき例外を返すこと() {
        int id = 0;
        Expense updateExpense = new Expense(id, Expense.Type.PROJECTED, "Updatedfood", 7777, LocalDate.of(2025, 10, 11), null, null);
        doReturn(Optional.empty()).when(expenseMapper).getExpenseById(id);

        assertThatThrownBy(() -> expenseServiceImpl.updateExpense(updateExpense))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Expense ID: " + id + " doesn't exist");

    }

    @Test
    public void getExpenseByIdで該当expenseを取得できること() {
        int id = 1;
        Optional<Expense> expense = Optional.of(new Expense(id, Expense.Type.ACTUAL, "food", 7777, LocalDate.of(2025, 1, 10), null, null));
        doReturn(expense).when(expenseMapper).getExpenseById(id);

        Expense actualExpense = expenseServiceImpl.getExpenseById(id);
        verify(expenseMapper, times(1)).getExpenseById(id);
    }

    @Test
    public void getExpenseByIdで存在しないid指定時に例外を返すこと() {
        int id = 0;
        doReturn(Optional.empty()).when(expenseMapper).getExpenseById(id);
        assertThatThrownBy(() -> expenseServiceImpl.getExpenseById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Expense ID: " + id + " doesn't exist");
    }

    @Test
    public void deleteExpenseで削除できること() {
        int id = 1;
        Optional<Expense> expense = Optional.of(new Expense(id, Expense.Type.ACTUAL, "food", 7777, LocalDate.of(2025, 1, 10), null, null));
        doReturn(expense).when(expenseMapper).getExpenseById(id);
        doNothing().when(expenseMapper).deleteExpense(id);
        expenseServiceImpl.deleteExpense(id);
        verify(expenseMapper, times(1)).deleteExpense(id);
    }

    @Test
    public void deleteExpenseで存在しないidのとき例外を返すこと() {
        int id = 0;
        doReturn(Optional.empty()).when(expenseMapper).getExpenseById(id);
        assertThatThrownBy(() -> expenseServiceImpl.deleteExpense(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Expense ID: " + id + " doesn't exist");
    }

}
