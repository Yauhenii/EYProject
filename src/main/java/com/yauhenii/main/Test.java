package com.yauhenii.main;

import com.yauhenii.main.service.ExcelSheetService;
import org.springframework.stereotype.Component;

@Component
public class Test {

    ExcelSheetService excelSheetService;

    public Test(ExcelSheetService excelSheetService) {
        this.excelSheetService=excelSheetService;
    }

    public void printAll(){
        excelSheetService.findAll().forEach(System.out::println);
    }
}
