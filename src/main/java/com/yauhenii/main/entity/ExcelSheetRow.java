package com.yauhenii.main.entity;

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
@Table (name = "TBS", schema = "eyproject")
public class ExcelSheetRow {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "bank_account")
    private int bankAccount;

    @Column (name = "opening_balance_asset")
    private double openingBalanceAsset;

    @Column (name = "opening_balance_liability")
    private double openingBalanceLiability;

    @Column (name = "turnover_credit")
    private double turnoverCredit;

    @Column (name = "turnover_debit")
    private double turnoverDebit;

    @Column (name = "closing_balance_asset")
    private double closingBalanceAsset;

    @Column (name = "closing_balance_liability")
    private double closingBalanceLiability;

    @Column
    private String filename;

}
