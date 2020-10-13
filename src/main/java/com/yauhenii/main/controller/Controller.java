package com.yauhenii.main.controller;


import com.yauhenii.main.model.sheet.entity.ExcelSheetRow;
import com.yauhenii.main.model.sheet.service.ExcelSheetService;
import com.yauhenii.main.model.sheetinfo.entity.ExcelSheetInfo;
import com.yauhenii.main.model.sheetinfo.service.ExcelSheetInfoService;
import com.yauhenii.main.utils.ExcelSheetParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class Controller {

    private final ExcelSheetService excelSheetService;
    private final ExcelSheetInfoService excelSheetInfoService;

    public Controller(ExcelSheetService excelSheetService,
        ExcelSheetInfoService excelSheetInfoService) {
        this.excelSheetService = excelSheetService;
        this.excelSheetInfoService = excelSheetInfoService;
    }

    @SneakyThrows
    @PostMapping("/add")
    public void addExcelSheet(@RequestParam("file") MultipartFile multipartFile) {
        File file = multipartToFile(multipartFile, multipartFile.getOriginalFilename());
        Object[] result = ExcelSheetParser.readFromExcel(file);
        excelSheetService.save((List<ExcelSheetRow>) result[0]);
        excelSheetInfoService.save((List<ExcelSheetInfo>) result[1]);
    }

    @SneakyThrows
    @PostMapping("/request/body")
    public ResponseEntity<Resource> getExcelSheet(@RequestParam("name") String fileName) {
        Resource resource = new ByteArrayResource(
            ExcelSheetParser.writeSheetToCsv(excelSheetService.getRowsByFilename(fileName))
                .getBytes());
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileName + "\"")
            .body(resource);
    }

    @SneakyThrows
    @PostMapping("/request/info")
    public ResponseEntity<Resource> getExcelSheetInfo(@RequestParam("name") String fileName) {
        Resource resource = new ByteArrayResource(
            ExcelSheetParser.writeInfoToCsv(excelSheetInfoService.getInfoByFilename(fileName))
                .getBytes());
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileName + "\"")
            .body(resource);
    }

    @SneakyThrows
    @GetMapping("/init")
    public ResponseEntity<Resource> getFileNames() {
        Resource resource = new ByteArrayResource(
            excelSheetService.getFilenames().getBytes());
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;")
            .body(resource);
    }

    public File multipartToFile(MultipartFile multipart, String fileName)
        throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        multipart.transferTo(convFile);
        return convFile;
    }

}
