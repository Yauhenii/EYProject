package com.yauhenii.main.model.sheetinfo.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBSINFO", schema = "eyproject")
public class ExcelSheetInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "period_start")
    private Date periodStart;

    @Column(name = "period_end")
    private Date periodEnd;

    @Column(name = "bank_name")
    private String bankName;

}
