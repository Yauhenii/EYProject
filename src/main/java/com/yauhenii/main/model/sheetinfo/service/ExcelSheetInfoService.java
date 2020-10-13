package com.yauhenii.main.model.sheetinfo.service;

import com.yauhenii.main.model.sheet.entity.ExcelSheetRow;
import com.yauhenii.main.model.sheet.repository.ExcelSheetRepository;
import com.yauhenii.main.model.sheetinfo.entity.ExcelSheetInfo;
import com.yauhenii.main.model.sheetinfo.repository.ExcelSheetInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ExcelSheetInfoService {

    private final ExcelSheetInfoRepository excelSheetInfoRepository;

    public List<ExcelSheetInfo> getInfoByFilename(String filename) {
        return excelSheetInfoRepository.findByFilename(filename);
    }

    public void save(List<ExcelSheetInfo> excelSheetInfoList) {
        excelSheetInfoRepository.saveAll(excelSheetInfoList);
    }

}
