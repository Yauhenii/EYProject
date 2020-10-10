package com.yauhenii.main;

import com.yauhenii.main.service.ExcelSheetService;
import com.yauhenii.main.utils.ExcelSheetParser;
import java.io.File;
import org.springframework.stereotype.Component;

@Component
public class Test {

    ExcelSheetService excelSheetService;

    public Test(ExcelSheetService excelSheetService) {
        this.excelSheetService=excelSheetService;
    }

    public void printByFile(String filename){
        excelSheetService.findByFilename(filename).forEach(System.out::println);
    }

    public void saveExcel(File file) {
        try {
            excelSheetService.save(ExcelSheetParser.readFromExcel(file));
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public void deleteSheet(String filename){
        excelSheetService.deleteByFilename(filename);
    }
}
