package household_expenses_managing_api.com.demo.form;

import household_expenses_managing_api.com.demo.entity.Income;
import lombok.Getter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class CreateIncomeForm {
    @NotNull
    private Income.Type type;

    @NotNull
    private String category;

    @Min(1)
    private int amount;

    @NotNull
    private LocalDate usedDate;

    public enum Type{
        PROJECTED, ACTUAL;
    }

    public CreateIncomeForm(Income.Type type, String category, int amount, LocalDate usedDate) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.usedDate = usedDate;
    }

    public Income convertToIncomeEntity(){
        Income income=new Income(this.type,this.category,this.amount,this.usedDate);
        return income;
    }
}
