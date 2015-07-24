package com.tri.erp.spring.model.enums;

/**
 * Created by Ryan D. Repe on 10/13/2014.
 */
public enum Workflow {

    APV(1),
    CV(2),
    RV(3),
    JV(4),
    MIR(5),
    SALES_VOUCHER(6),
    CASH_RECEIPTS(7),
    BANK_DEPOSIT(8);

    private int id;

    Workflow(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}