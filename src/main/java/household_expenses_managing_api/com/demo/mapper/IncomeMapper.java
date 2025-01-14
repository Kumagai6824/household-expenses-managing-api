package household_expenses_managing_api.com.demo.mapper;

import household_expenses_managing_api.com.demo.entity.Income;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IncomeMapper {
    @Select("SELECT * FROM income")
    List<Income> getAllIncome();
}
