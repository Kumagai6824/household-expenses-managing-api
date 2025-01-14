package household_expenses_managing_api.com.demo.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

public class Income {
    private int id;

    private Type type;
    private String category;
    private BigDecimal amount;
    private LocalDate usedDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public enum Type {
        PROJECTED, ACTUAL;
    }

    public Income() {
    }

    public Income(int id, Type type, String category, BigDecimal amount, LocalDate usedDate, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.usedDate = usedDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Income{" +
                "id=" + id +
                ", type=" + type +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", usedDate=" + usedDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(LocalDate usedDate) {
        this.usedDate = usedDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Income income)) return false;
        return id == income.id && type == income.type && category.equals(income.category) && amount.equals(income.amount) && usedDate.equals(income.usedDate) && Objects.equals(createdAt, income.createdAt) && Objects.equals(updatedAt, income.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, category, amount, usedDate, createdAt, updatedAt);
    }
}
