package com.example.tabulatest.service.impl;

import com.example.tabulatest.models.Expense;
import com.example.tabulatest.service.BankStatementParser;
import com.example.tabulatest.transformer.DataTransformer;
import com.example.tabulatest.transformer.impl.DateDataTransformer;
import com.example.tabulatest.transformer.impl.DecimalDataTransformer;
import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BankStatementParserBRD implements BankStatementParser {
    final String filePath;
    final String dateFormat = "dd.MM.yyyy";
    final String decimalFormat = "#.##";
    List<Expense> expensesList;

    public BankStatementParserBRD(String filePath) {
        this.filePath = filePath;
        this.expensesList = new ArrayList<>();
    }

    @Override
    public void parseBankStatement() throws IOException {
        InputStream in = this.getClass().getResourceAsStream(this.filePath);
        try (PDDocument document = PDDocument.load(in)) {
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            PageIterator pi = new ObjectExtractor(document).extract();
            while (pi.hasNext()) {
                Page page = pi.next();
                List<Table> tables = sea.extract(page);
                processTables(tables);
            }
        }
    }

    private void processTables(List<Table> tables) {
        for (int i = 2; i < tables.size(); i++) {
            List<List<RectangularTextContainer>> rows = tables.get(i).getRows();
            for (List<RectangularTextContainer> cells : rows) {
                Expense expense = createExpenseFromCells(cells);
                if (expense.getExpenseDate() != null) {
                    expensesList.add(expense);
                }
            }
        }
    }

    private Expense createExpenseFromCells(List<RectangularTextContainer> cells) {
        Expense expense = new Expense();
        for (int j = 0; j < cells.size(); j++) {
            RectangularTextContainer content = cells.get(j);
            String text = content.getText().replace("\r", " ").trim();

            switch (j) {
                case 1 -> {
                    DataTransformer<LocalDate> dateTransformer = new DateDataTransformer(dateFormat);
                    if (dateTransformer.isValidData(text)) {
                        LocalDate date = dateTransformer.getData(text);
                        expense.setExpenseDate(date.atStartOfDay());
                    }
                }
                case 3 -> expense.setDescription(text);
                case 5 -> {
                    DataTransformer<BigDecimal> decimalTransformer = new DecimalDataTransformer(decimalFormat);
                    BigDecimal amount = decimalTransformer.getData(text);
                    expense.setAmount(amount);
                }
                default -> {
                }
            }
        }
        return expense;
    }

    @Override
    public List<Expense> getBankStatementExpenses() {
        return this.expensesList;
    }
}