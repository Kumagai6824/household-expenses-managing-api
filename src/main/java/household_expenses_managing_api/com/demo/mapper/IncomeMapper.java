package household_expenses_managing_api.com.demo.mapper;

import household_expenses_managing_api.com.demo.entity.Income;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface IncomeMapper {
    @Select("SELECT * FROM income")
    List<Income> getAllIncome();

    @Insert("INSERT INTO income (type, category, amount , used_date, created_at, updated_at ) VALUES (#{type}, #{category}, #{amount} , #{usedDate}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addIncome(Income income);

    @Update("UPDATE income SET type = #{type}, category = #{category}, amount = #{amount}, used_date = #{usedDate}, updated_at = #{updatedAt} WHERE id = #{id}")
    void updateIncome(Income income);

    @Select("SELECT * FROM income WHERE id = #{id}")
    Optional<Income> getIncomeById(int id);
}
