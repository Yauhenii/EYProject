package com.yauhenii.main.model.sheet.repository;

import com.yauhenii.main.model.sheet.entity.ExcelSheetRow;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcelSheetRepository extends JpaRepository<ExcelSheetRow, Integer> {

    List<ExcelSheetRow> findByFilename(String filename);

    Long deleteAllByFilename(String filename);

}
