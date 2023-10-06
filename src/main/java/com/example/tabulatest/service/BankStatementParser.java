package com.example.tabulatest.service;

import com.example.tabulatest.models.Expense;

import java.io.IOException;
import java.util.List;

public interface BankStatementParser {
    void parseBankStatement() throws IOException;

    List<Expense> getBankStatementExpenses();
}
