package com.tri.erp.spring.commons.facade;

import com.tri.erp.spring.model.Transaction;

import java.util.Date;

/**
 * Created by TSI Admin on 5/5/2015.
 */
public interface GeneratorFacade {
    public Integer entityAccountNumber();
    public Transaction transaction();
    public String voucherCode(String prefix, String latestCode, Date voucherDate);

}
