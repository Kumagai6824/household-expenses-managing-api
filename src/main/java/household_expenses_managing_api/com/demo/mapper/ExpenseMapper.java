package household_expenses_managing_api.com.demo.mapper;

import household_expenses_managing_api.com.demo.entity.Expense;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ExpenseMapper {
    @Select("SELECT * FROM expense")
    List<Expense> getAllExpense();

    @Insert("INSERT INTO expense (type, category, amount , used_date, created_at, updated_at ) VALUES (#{type}, #{category}, #{amount} , #{usedDate}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addExpense(Expense expense);

    @Update("UPDATE expense SET type = #{type}, category = #{category}, amount = #{amount}, used_date = #{usedDate}, updated_at = #{updatedAt} WHERE id = #{id}")
    void updateExpense(Expense expense);

    @Select("SELECT * FROM expense WHERE id = #{id}")
    Optional<Expense> getExpenseById(int id);

    @Delete("DELETE FROM expense WHERE id = #{id}")
    void deleteExpense(int id);
}
