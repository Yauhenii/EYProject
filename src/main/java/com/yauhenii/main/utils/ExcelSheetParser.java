package com.yauhenii.main.utils;

import com.yauhenii.main.model.sheet.entity.ExcelSheetRow;
import com.yauhenii.main.model.sheetinfo.entity.ExcelSheetInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.crypto.Data;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class ExcelSheetParser {

    private static final String SHEET_NAME = "Sheet1";
    public static final String CSV_FILE_NAME = "table.csv";
    private static final int HEADER_LENGTH = 9;

    private static final int BANK_NAME_ROW = 0;
    private static final int BANK_NAME_COL = 0;
    private static final int PERIOD_ROW = 2;
    private static final int PERIOD_COL = 0;

    enum Column {
        BANK_ACCOUNT,
        OPPENING_BALANCE_ASSET,
        OPPENING_BALANCE_LIABILITY,
        TURNOVER_DEBIT,
        TURNOVER_CREDIT,
        CLOSING_BALANCE_ASSET,
        CLOSING_BALANCE_LIABILITY,
    }

    public static Object[] readFromExcel(File file) throws IOException, ParseException {
        HSSFWorkbook excelWorkbook = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet excelSheet = excelWorkbook.getSheet(SHEET_NAME);
        String filename = file.getName();

        //parse header

        List<ExcelSheetInfo> resultExcelSheetInfo = new ArrayList<>();
        String bankName = excelSheet.getRow(BANK_NAME_ROW).getCell(BANK_NAME_COL)
            .getStringCellValue();
        String period = excelSheet.getRow(PERIOD_ROW).getCell(PERIOD_COL).getStringCellValue();
        String[] periodSplit = period.split(" ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date periodStart = dateFormat.parse(periodSplit[3]);
        Date periodEnd = dateFormat.parse(periodSplit[5]);

        resultExcelSheetInfo.add(new ExcelSheetInfo(0, filename, periodStart, periodEnd, bankName));

        //parse body

        List<ExcelSheetRow> resultExcelSheetRow = new ArrayList<>();
        int bankAccount;
        double openingBalanceAsset;
        double openingBalanceLiability;
        double turnoverCredit;
        double turnoverDebit;
        double closingBalanceAsset;
        double closingBalanceLiability;

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

        return new Object[]{resultExcelSheetRow, resultExcelSheetInfo};
    }

    public static String writeSheetToCsv(List<ExcelSheetRow> excelSheet) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ExcelSheetRow row : excelSheet) {
            stringBuilder.append(row.getBankAccount());
            stringBuilder.append(",");
            stringBuilder.append(row.getOpeningBalanceAsset());
            stringBuilder.append(",");
            stringBuilder.append(row.getOpeningBalanceLiability());
            stringBuilder.append(",");
            stringBuilder.append(row.getTurnoverDebit());
            stringBuilder.append(",");
            stringBuilder.append(row.getTurnoverCredit());
            stringBuilder.append(",");
            stringBuilder.append(row.getClosingBalanceAsset());
            stringBuilder.append(",");
            stringBuilder.append(row.getOpeningBalanceLiability());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static String writeInfoToCsv(List<ExcelSheetInfo> excelSheetInfoList) {
        StringBuilder stringBuilder = new StringBuilder();
        ExcelSheetInfo excelSheetInfo = excelSheetInfoList.get(0);
        stringBuilder.append(excelSheetInfo.getBankName());
        stringBuilder.append(",");
        stringBuilder.append(excelSheetInfo.getPeriodStart());
        stringBuilder.append(",");
        stringBuilder.append(excelSheetInfo.getPeriodEnd());
        return stringBuilder.toString();
    }

}
