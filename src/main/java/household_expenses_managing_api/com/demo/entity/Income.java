package household_expenses_managing_api.com.demo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
@Setter
public class Income {
    private int id;

    private Type type;
    private String category;
    private int amount;
    private LocalDate usedDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public enum Type {
        PROJECTED, ACTUAL;
    }

    public Income(int id, Type type, String category, int amount, LocalDate usedDate, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.usedDate = usedDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Income(Type type, String category, int amount, LocalDate usedDate, Timestamp createdAt, Timestamp updatedAt) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.usedDate = usedDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Income(Type type, String category, int amount, LocalDate usedDate) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.usedDate = usedDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Income(int id, Type type, String category, int amount, LocalDate usedDate) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.usedDate = usedDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Income income)) return false;
        return id == income.id && amount == income.amount && type == income.type && Objects.equals(category, income.category) && Objects.equals(usedDate, income.usedDate) && Objects.equals(createdAt, income.createdAt) && Objects.equals(updatedAt, income.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, category, amount, usedDate, createdAt, updatedAt);
    }
}
