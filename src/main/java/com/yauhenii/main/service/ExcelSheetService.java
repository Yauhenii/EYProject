package com.yauhenii.main.service;

import com.yauhenii.main.entity.ExcelSheet;
import com.yauhenii.main.repository.ExcelSheetRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExcelSheetService {

    private final ExcelSheetRepository excelSheetRepository;

    public List<ExcelSheet> findAll(){
        return excelSheetRepository.findAll();
    }
}
