package household_expenses_managing_api.com.demo.mapper;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import household_expenses_managing_api.com.demo.entity.Income;
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
@DataSet(value = "income.yml", executeScriptsBefore = "reset-id.sql", cleanAfter = true, transactional = true)
class IncomeMapperTest {
    @Autowired
    IncomeMapper incomeMapper;

    @Test
    @Transactional
    void 全incomeが取得できること() {
        List<Income> incomes = incomeMapper.getAllIncome();
        assertThat(incomes)
                .hasSize(1)
                .contains(
                        new Income(1, Income.Type.ACTUAL, "salary", 5000, LocalDate.of(2024, 1, 10), null, null)
                );
    }

    @Test
    @DataSet(value = "empty-income.yml")
    @Transactional
    void incomeテーブルが空の時からで返ること() {
        List<Income> incomes = incomeMapper.getAllIncome();
        assertThat(incomes).isEmpty();
    }

    @Test
    @Transactional
    void addIncomeでレコードが追加されること() {
        Income income = new Income(Income.Type.PROJECTED, "bonus", 100000, LocalDate.of(2025, 1, 1), null, null);
        incomeMapper.addIncome(income);

        int incomeId = income.getId();
        assertNotNull(incomeId);
        assertThat(incomeMapper.getAllIncome()).contains(income);
    }

    @Test
    @Transactional
    void updateIncomeでレコードが更新されること() {
        incomeMapper.updateIncome(new Income(1, Income.Type.PROJECTED, "Updated salary", 55555, LocalDate.of(2025, 10, 10), null, null));
        assertThat(incomeMapper.getAllIncome()).contains(new Income(1, Income.Type.PROJECTED, "Updated salary", 55555, LocalDate.of(2025, 10, 10), null, null));
    }

    @Test
    @Transactional
    void getIncomeByIdで該当incomeが取得できること() {
        Optional<Income> income = incomeMapper.getIncomeById(1);
        assertThat(income)
                .contains(new Income(1, Income.Type.ACTUAL, "salary", 5000, LocalDate.of(2024, 1, 10), null, null));
    }

    @Test
    @Transactional
    void getIncomeByIdで存在しないidのとき空が取得されること() {
        Optional<Income> income = incomeMapper.getIncomeById(100);
        assertThat(income).isEmpty();
    }

    @Test
    @Transactional
    void deleteIncomeでレコードが削除されること() {
        int id = 1;
        incomeMapper.deleteIncome(id);
        assertThat(incomeMapper.getIncomeById(id)).isEmpty();
    }

    @Test
    @Transactional
    void deleteIncomeで存在しないidのとき何も変更されないこと() {
        int id = 0;
        incomeMapper.deleteIncome(id);
        assertThat(incomeMapper.getAllIncome()).contains(new Income(1, Income.Type.ACTUAL, "salary", 5000, LocalDate.of(2024, 1, 10), null, null));
    }
}

