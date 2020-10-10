package com.yauhenii.main.service;

import com.yauhenii.main.entity.ExcelSheetRow;
import com.yauhenii.main.repository.ExcelSheetRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ExcelSheetService {

    private final ExcelSheetRepository excelSheetRepository;

    public List<ExcelSheetRow> findByFilename(String filename){
        return excelSheetRepository.findByFilename(filename);
    }

    public void deleteByFilename(String filename){
        excelSheetRepository.deleteAllByFilename(filename);
    }

    public void save(List<ExcelSheetRow> excelSheetRowList){
        excelSheetRepository.saveAll(excelSheetRowList);
    }

}
