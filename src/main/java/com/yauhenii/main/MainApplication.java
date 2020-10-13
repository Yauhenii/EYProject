package com.yauhenii.main;

import com.yauhenii.main.model.sheet.service.ExcelSheetService;
import java.awt.EventQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainApplication {

    @Autowired
    public static ExcelSheetService excelSheetService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(
            MainApplication.class)
            .headless(false)
            .run(args);
    }
}
