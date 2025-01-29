package household_expenses_managing_api.com.demo.mapper;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import household_expenses_managing_api.com.demo.entity.Expense;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DBRider
@DataSet(value = "expense.yml", executeScriptsBefore = "reset-id.sql", cleanAfter = true, transactional = true)
class ExpenseMapperTest {
    @Autowired
    ExpenseMapper expenseMapper;

    @Test
    @Transactional
    void 全expenseが取得できること() {
        List<Expense> expenses = expenseMapper.getAllExpense();
        assertThat(expenses)
                .hasSize(1)
                .contains(
                        new Expense(1, Expense.Type.ACTUAL, "food", 7000, LocalDate.of(2025, 1, 10), null, null)
                );
    }

    @Test
    @DataSet(value = "empty-expense.yml")
    @Transactional
    void expenseテーブルが空の時からで返ること() {
        List<Expense> expenses = expenseMapper.getAllExpense();
        assertThat(expenses).isEmpty();
    }

    @Test
    @Transactional
    void addExpenseでレコードが追加されること() {
        Expense expense = new Expense(Expense.Type.PROJECTED, "Rent", 70000, LocalDate.of(2025, 1, 1), null, null);
        expenseMapper.addExpense(expense);

        int expenseId = expense.getId();
        assertNotNull(expenseId);
        assertThat(expenseMapper.getAllExpense()).contains(expense);
    }

    @Test
    @Transactional
    void updateExpenseでレコードが更新されること() {
        expenseMapper.updateExpense(new Expense(1, Expense.Type.PROJECTED, "Updated food", 8000, LocalDate.of(2025, 10, 10), null, null));
        assertThat(expenseMapper.getAllExpense()).contains(new Expense(1, Expense.Type.PROJECTED, "Updated food", 8000, LocalDate.of(2025, 10, 10), null, null));
    }

    @Test
    @Transactional
    void getExpenseByIdで該当expenseが取得できること() {
        Optional<Expense> expense = expenseMapper.getExpenseById(1);
        assertThat(expense)
                .contains(new Expense(1, Expense.Type.ACTUAL, "food", 7000, LocalDate.of(2025, 1, 10), null, null));
    }

    @Test
    @Transactional
    void getExpenseByIdで存在しないidのとき空が取得されること() {
        Optional<Expense> expense = expenseMapper.getExpenseById(100);
        assertThat(expense).isEmpty();
    }

    @Test
    @Transactional
    void deleteExpenseでレコードが削除されること() {
        int id = 1;
        expenseMapper.deleteExpense(id);
        assertThat(expenseMapper.getExpenseById(id)).isEmpty();
    }

    @Test
    @Transactional
    void deleteExpenseで存在しないidのとき何も変更されないこと() {
        int id = 0;
        expenseMapper.deleteExpense(id);
        assertThat(expenseMapper.getAllExpense()).contains(new Expense(1, Expense.Type.ACTUAL, "food", 7000, LocalDate.of(2025, 1, 10), null, null));
    }
}

