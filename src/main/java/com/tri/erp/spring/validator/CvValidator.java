package com.tri.erp.spring.validator;

import com.tri.erp.spring.commons.Debug;
import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.dtoers.LedgerDtoerImpl;
import com.tri.erp.spring.model.CheckVoucher;
import com.tri.erp.spring.response.SubLedgerDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * Created by TSI Admin on 9/11/2014.
 */

@Component
public class CvValidator implements Validator {

    LedgerDtoerImpl ledgerDtoer;

    @Override
    public boolean supports(Class<?> aClass) {
        return CheckVoucher.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CheckVoucher cv = (CheckVoucher) o;
        CommonValidator.validateJournal(errors, cv.getTransaction(), cv.getGeneralLedgerLines(), cv.getSubLedgerLines());
    }

    // this how we add dependency, @Autowired is not working here!
    public void setLedgerDtoer(LedgerDtoerImpl l) {
        this.ledgerDtoer = l;
    }
}
