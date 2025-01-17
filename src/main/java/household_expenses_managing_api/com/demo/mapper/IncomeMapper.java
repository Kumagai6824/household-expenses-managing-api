package household_expenses_managing_api.com.demo.mapper;

import household_expenses_managing_api.com.demo.entity.Income;
import oracle.jdbc.proxy.annotation.Post;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IncomeMapper {
    @Select("SELECT * FROM income")
    List<Income> getAllIncome();

    @Insert("INSERT INTO income (type, category, amount , used_date, created_at, updated_at ) VALUES (#{type}, #{category}, #{amount} , #{usedDate}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void addIncome(Income income);
}
