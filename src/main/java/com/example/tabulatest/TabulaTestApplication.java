package com.example.tabulatest;

import com.example.tabulatest.models.Expense;
import com.example.tabulatest.service.BankStatementParser;
import com.example.tabulatest.service.impl.BankStatementParserBRD;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class TabulaTestApplication {

    public static void main(String[] args) throws IOException {
        BankStatementParser parser = new BankStatementParserBRD("/pdf/extras.pdf");

        parser.parseBankStatement();

        List<Expense> expensesList = parser.getBankStatementExpenses();
        for (Expense expense : expensesList) {
            System.out.println(expense);
        }
    }
}
