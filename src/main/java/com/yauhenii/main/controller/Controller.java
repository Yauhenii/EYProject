package com.yauhenii.main.controller;


import com.yauhenii.main.service.ExcelSheetService;
import com.yauhenii.main.utils.ExcelSheetParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    public Controller(ExcelSheetService excelSheetService) {
        this.excelSheetService = excelSheetService;
    }

    @SneakyThrows
    @PostMapping("/add")
    public void addExcelSheet(@RequestParam("file") MultipartFile multipartFile) {
        File file = multipartToFile(multipartFile, multipartFile.getOriginalFilename());
        excelSheetService.save(ExcelSheetParser.readFromExcel(file));
    }

    @SneakyThrows
    @PostMapping("/request")
    public ResponseEntity<Resource> getExcelSheet(@RequestParam("name") String fileName) {
        Resource resource = new ByteArrayResource(
            ExcelSheetParser.writeToCsv(excelSheetService.findByFilename(fileName)).getBytes());
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileName + "\"")
            .body(resource);
    }

    @SneakyThrows
    @GetMapping("/init")
    public ResponseEntity<Resource> getFileNames() {
        Resource resource = new ByteArrayResource(
            excelSheetService.findFileName().getBytes());
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;")
            .body(resource);
    }

//    @SneakyThrows
//    @PostMapping("/request")
//    public ResponseEntity<Resource> getExcelSheet(@RequestParam("name") String fileName) {
//        File file = ExcelSheetParser.writeToCsv(excelSheetService.findByFilename(fileName));
//        Resource resource = new InputStreamResource(new FileInputStream(file));
//        return ResponseEntity.ok()
//            .header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getName() + "\"")
//            .contentLength(file.length())
//            .body(resource);
//    }

    public File multipartToFile(MultipartFile multipart, String fileName)
        throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        multipart.transferTo(convFile);
        return convFile;
    }

}
