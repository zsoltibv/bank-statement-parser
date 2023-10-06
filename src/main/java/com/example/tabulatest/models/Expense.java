package com.example.tabulatest.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Expense {
    private String description;
    private BigDecimal amount;
    private LocalDateTime expenseDate;

    public Expense(String description, BigDecimal amount, LocalDateTime expenseDate) {
        this.description = description;
        this.amount = amount;
        this.expenseDate = expenseDate;
    }

    public Expense() {
    }

    @Override
    public String toString() {
        return "description: " + description +
                " | amount: " + amount +
                " | expenseDate: " + expenseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDateTime expenseDate) {
        this.expenseDate = expenseDate;
    }
}
