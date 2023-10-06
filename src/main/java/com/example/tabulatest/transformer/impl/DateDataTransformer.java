package com.example.tabulatest.transformer.impl;
import com.example.tabulatest.transformer.DataTransformer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateDataTransformer implements DataTransformer<LocalDate> {
    DateTimeFormatter formatter;

    public DateDataTransformer(String dateFormat) {
        this.formatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    @Override
    public boolean isValidData(String dateStr) {
        try {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public LocalDate getData(String dateStr) {
        if (isValidData(dateStr)) {
            return LocalDate.parse(dateStr, formatter);
        }
        return null;
    }
}