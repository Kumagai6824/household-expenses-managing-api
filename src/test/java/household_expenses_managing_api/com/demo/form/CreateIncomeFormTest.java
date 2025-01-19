package household_expenses_managing_api.com.demo.form;

import household_expenses_managing_api.com.demo.entity.Income;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class CreateIncomeFormTest {
    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        Locale.setDefault(Locale.JAPAN);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void typeがnullのときバリデーションエラーとなること() {
        CreateIncomeForm createIncomeForm = new CreateIncomeForm(null, "Test income", 100, LocalDate.of(2025, 1, 1));
        Set<ConstraintViolation<CreateIncomeForm>> violations = validator.validate(createIncomeForm);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("type", "null は許可されていません"));
    }

    @Test
    public void categoryがnullのときバリデーションエラーとなること() {
        CreateIncomeForm createIncomeForm = new CreateIncomeForm(Income.Type.ACTUAL, null, 100, LocalDate.of(2025, 1, 1));
        Set<ConstraintViolation<CreateIncomeForm>> violations = validator.validate(createIncomeForm);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("category", "null は許可されていません"), tuple("category", "空白は許可されていません"));
    }

    @Test
    public void categoryが空のときバリデーションエラーとなること() {
        CreateIncomeForm createIncomeForm = new CreateIncomeForm(Income.Type.ACTUAL, "", 100, LocalDate.of(2025, 1, 1));
        Set<ConstraintViolation<CreateIncomeForm>> violations = validator.validate(createIncomeForm);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("category", "空白は許可されていません"));
    }

    @Test
    public void categoryが51文字以上のときバリデーションエラーとなること() {
        String category = "Bonus";
        CreateIncomeForm createIncomeForm = new CreateIncomeForm(Income.Type.ACTUAL, category.repeat(10) + "1", 100, LocalDate.of(2025, 1, 1));
        Set<ConstraintViolation<CreateIncomeForm>> violations = validator.validate(createIncomeForm);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("category", "0 から 50 の間のサイズにしてください"));
    }

    @Test
    public void amountが0のときバリデーションエラーとなること() {
        CreateIncomeForm createIncomeForm = new CreateIncomeForm(Income.Type.ACTUAL, "Test income", 0, LocalDate.of(2025, 1, 1));
        Set<ConstraintViolation<CreateIncomeForm>> violations = validator.validate(createIncomeForm);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("amount", "1 以上の値にしてください"));
    }

    @Test
    public void usedDateがnullのときバリデーションエラーとなること() {
        CreateIncomeForm createIncomeForm = new CreateIncomeForm(Income.Type.ACTUAL, "Test income", 100, null);
        Set<ConstraintViolation<CreateIncomeForm>> violations = validator.validate(createIncomeForm);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("usedDate", "null は許可されていません"));
    }
}
