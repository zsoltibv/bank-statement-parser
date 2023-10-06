package com.example.tabulatest.transformer.impl;

import com.example.tabulatest.transformer.DataTransformer;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

public class DecimalDataTransformer implements DataTransformer<BigDecimal> {
    private final DecimalFormat formatter;

    public DecimalDataTransformer(String decimalFormat) {
        this.formatter = new DecimalFormat(decimalFormat);
    }

    @Override
    public boolean isValidData(String decimalStr) {
        try {
            formatter.parse(decimalStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public BigDecimal getData(String decimalStr) {
        if(!decimalStr.isEmpty()) {
            decimalStr = decimalStr.substring(1);
        }

        try {
            return BigDecimal.valueOf(formatter.parse(decimalStr).doubleValue());
        } catch (ParseException e) {
            return BigDecimal.ZERO;
        }
    }
}
