package com.yauhenii.main.service;

import com.yauhenii.main.entity.ExcelSheetRow;
import com.yauhenii.main.repository.ExcelSheetRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ExcelSheetService {

    private final ExcelSheetRepository excelSheetRepository;

    public List<ExcelSheetRow> findByFilename(String filename) {
        return excelSheetRepository.findByFilename(filename);
    }

    public void deleteByFilename(String filename) {
        excelSheetRepository.deleteAllByFilename(filename);
    }

    public void save(List<ExcelSheetRow> excelSheetRowList) {
        excelSheetRepository.saveAll(excelSheetRowList);
    }

    public String findFileName() {
        List<ExcelSheetRow> excelSheet = excelSheetRepository.findAll();
        HashSet<String> hashSet = new HashSet<>();
        for (ExcelSheetRow row : excelSheet) {
            hashSet.add(row.getFilename());
        }
        String resultString = hashSet.stream().collect(Collectors.joining(" "));
        return resultString;
    }

}
