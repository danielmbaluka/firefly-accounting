package com.tri.erp.spring.commons.facade;

import com.tri.erp.spring.commons.GlobalConstant;
import com.tri.erp.spring.model.EntityAccountNumber;
import com.tri.erp.spring.model.Transaction;
import com.tri.erp.spring.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

/**
 * Created by TSI Admin on 5/5/2015.
 */

@Component
public class GeneratorFacadeImpl implements GeneratorFacade {

    @Autowired
    AuthenticationFacade authenticationFacade;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Integer entityAccountNumber() {
        User user = authenticationFacade.getLoggedIn();

        EntityAccountNumber accountNumber = new EntityAccountNumber(user);

        entityManager.persist(accountNumber);
        entityManager.flush();

        return accountNumber.getId();
    }

    @Override
    public Transaction transaction() {
        User user = authenticationFacade.getLoggedIn();

        Transaction trans = new Transaction(user, new Date());

        entityManager.persist(trans);
        entityManager.flush();

        return trans;
    }

    @Override
    public String voucherCode(String prefix, String latestCode, Date voucherDate) {
        String code = null;
        NumberFormat ctrFormatter = new DecimalFormat("0000");

        try {
            String thisYear = GlobalConstant.YYYY_DATE_FORMAT.format(voucherDate);

            if (latestCode == null || latestCode.equalsIgnoreCase("")) {  // maybe first time
                code =  prefix + "-" + thisYear + "-" + "0001";
            } else {
                String[] latestCodeArr = latestCode.split("-");
                prefix = latestCodeArr[0];
                String counterPart = latestCodeArr[2];  // counter
                int intCounterPart = Integer.parseInt(counterPart); // convert counter

                intCounterPart++; // just another voucher of the year
                counterPart = ctrFormatter.format(intCounterPart); // format counter

                code = prefix + "-" + thisYear + "-" + counterPart;
            }
        } catch (Exception ex) {
            Logger.getRootLogger().error(ex.getMessage());
        }
        return code;
    }
}
