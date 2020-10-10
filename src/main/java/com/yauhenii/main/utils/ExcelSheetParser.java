package com.yauhenii.main.utils;

import com.yauhenii.main.entity.ExcelSheetRow;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class ExcelSheetParser {

    private final static String SHEET_NAME = "Sheet1";
    private final static int HEADER_LENGTH = 9;

    enum Column {
        BANK_ACCOUNT,
        OPPENING_BALANCE_ASSET,
        OPPENING_BALANCE_LIABILITY,
        TURNOVER_DEBIT,
        TURNOVER_CREDIT,
        CLOSING_BALANCE_ASSET,
        CLOSING_BALANCE_LIABILITY,
    }

    public static List<ExcelSheetRow> readFromExcel(File file) throws IOException {
        HSSFWorkbook excelWorkbook = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet excelSheet = excelWorkbook.getSheet(SHEET_NAME);

        List<ExcelSheetRow> resultExcelSheetRow = new ArrayList<>();
        int bankAccount;
        double openingBalanceAsset;
        double openingBalanceLiability;
        double turnoverCredit;
        double turnoverDebit;
        double closingBalanceAsset;
        double closingBalanceLiability;
        String filename = file.getName();

        for (Row excelRow : excelSheet) {
            //skip header
            if (excelRow.getRowNum() < HEADER_LENGTH) {
                continue;
            }
            //read
            //skip unnecessary information
            try {
                bankAccount = Integer.parseInt(excelRow.getCell(Column.BANK_ACCOUNT.ordinal())
                    .getStringCellValue());
            } catch (Exception exception) {
                continue;
            }
            openingBalanceAsset = excelRow.getCell(Column.OPPENING_BALANCE_ASSET.ordinal())
                .getNumericCellValue();
            openingBalanceLiability = excelRow.getCell(Column.OPPENING_BALANCE_LIABILITY.ordinal())
                .getNumericCellValue();
            turnoverCredit = excelRow.getCell(Column.TURNOVER_CREDIT.ordinal())
                .getNumericCellValue();
            turnoverDebit = excelRow.getCell(Column.TURNOVER_DEBIT.ordinal()).getNumericCellValue();
            closingBalanceAsset = excelRow.getCell(Column.CLOSING_BALANCE_ASSET.ordinal())
                .getNumericCellValue();
            closingBalanceLiability = excelRow.getCell(Column.CLOSING_BALANCE_LIABILITY.ordinal())
                .getNumericCellValue();

            resultExcelSheetRow.add(
                new ExcelSheetRow(0, bankAccount, openingBalanceAsset, openingBalanceLiability,
                    turnoverCredit, turnoverDebit, closingBalanceAsset, closingBalanceLiability,
                    filename));
        }
        return resultExcelSheetRow;
    }

}
