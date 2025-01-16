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

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DBRider
@DataSet(value = "income.yml",executeScriptsBefore = "reset-id.sql", cleanAfter = true,transactional = true)
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
    void incomeテーブルが空の時からで返ること(){
        List<Income> incomes = incomeMapper.getAllIncome();
        assertThat(incomes).isEmpty();
    }
}

