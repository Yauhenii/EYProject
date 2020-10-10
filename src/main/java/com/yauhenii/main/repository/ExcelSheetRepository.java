package com.yauhenii.main.repository;

import com.yauhenii.main.entity.ExcelSheetRow;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExcelSheetRepository extends JpaRepository<ExcelSheetRow, Integer> {


    List<ExcelSheetRow> findByFilename(String filename);

    Long deleteAllByFilename(String filename);

}
