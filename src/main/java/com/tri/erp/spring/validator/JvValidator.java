package com.tri.erp.spring.validator;

import com.tri.erp.spring.dtoers.LedgerDtoerImpl;
import com.tri.erp.spring.model.CheckVoucher;
import com.tri.erp.spring.model.JournalVoucher;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by TSI Admin on 9/11/2014.
 */

@Component
public class JvValidator implements Validator {

    LedgerDtoerImpl ledgerDtoer;

    @Override
    public boolean supports(Class<?> aClass) {
        return CheckVoucher.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        JournalVoucher jv = (JournalVoucher) o;
        CommonValidator.validateJournal(errors, jv.getTransaction(), jv.getGeneralLedgerLines(), jv.getSubLedgerLines());
    }

    // this how we add dependency, @Autowired is not working here!
    public void setLedgerDtoer(LedgerDtoerImpl l) {
        this.ledgerDtoer = l;
    }
}
