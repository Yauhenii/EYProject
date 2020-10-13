package com.yauhenii.main.model.sheetinfo.repository;

import com.yauhenii.main.model.sheetinfo.entity.ExcelSheetInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcelSheetInfoRepository extends JpaRepository<ExcelSheetInfo, Integer> {

    List<ExcelSheetInfo> findByFilename(String filename);

}
