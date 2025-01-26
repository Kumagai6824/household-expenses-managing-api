package household_expenses_managing_api.com.demo.mapper;

import household_expenses_managing_api.com.demo.entity.Income;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface IncomeMapper {
    @Select("SELECT * FROM income")
    List<Income> getAllIncome();

    @Insert("INSERT INTO income (type, category, amount , used_date, created_at, updated_at ) VALUES (#{type}, #{category}, #{amount} , #{usedDate}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addIncome(Income income);

    @Update("UPDATE income SET type = #{type}, category = #{category}, amount = #{amount}, used_date = #{used_date} WHERE id = #{id}")
    void updateIncome(int id, Income.Type type, String category, int amount, LocalDate used_date);
}
