package household_expenses_managing_api.com.demo.form;

import household_expenses_managing_api.com.demo.entity.Income;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateIncomeForm {
    private int id;

    @NotNull
    private Income.Type type;

    @NotBlank
    @NotNull
    @Size(max = 50)
    private String category;

    @Min(1)
    private int amount;

    @NotNull
    private LocalDate usedDate;

    public enum Type {
        PROJECTED, ACTUAL;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UpdateIncomeForm(int id, Income.Type type, String category, int amount, LocalDate usedDate) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.usedDate = usedDate;
    }

    public Income convertToIncomeEntity() {
        Income income = new Income(this.id, this.type, this.category, this.amount, this.usedDate);
        return income;
    }
}
