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
public class Expense {
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

    public Expense(int id, Type type, String category, int amount, LocalDate usedDate, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.usedDate = usedDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Expense(Type type, String category, int amount, LocalDate usedDate, Timestamp createdAt, Timestamp updatedAt) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.usedDate = usedDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Expense(Type type, String category, int amount, LocalDate usedDate) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.usedDate = usedDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Expense(int id, Type type, String category, int amount, LocalDate usedDate) {
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
        if (!(o instanceof Expense expense)) return false;
        return id == expense.id && amount == expense.amount && type == expense.type && Objects.equals(category, expense.category) && Objects.equals(usedDate, expense.usedDate) && Objects.equals(createdAt, expense.createdAt) && Objects.equals(updatedAt, expense.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, category, amount, usedDate, createdAt, updatedAt);
    }
}
