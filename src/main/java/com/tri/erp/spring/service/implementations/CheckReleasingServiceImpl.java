package com.tri.erp.spring.service.implementations;

import com.tri.erp.spring.model.CheckVoucherCheque;
import com.tri.erp.spring.model.ReleasedCheque;
import com.tri.erp.spring.repo.*;
import com.tri.erp.spring.response.PostResponse;
import com.tri.erp.spring.service.interfaces.CheckReleasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

/**
 * Created by TSI Admin on 6/19/2015.
 */

@Service
public class CheckReleasingServiceImpl implements CheckReleasingService {

    @Autowired
    CheckVoucherChequeRepo chequeRepo;

    @Autowired
    ReleasedCheckRepo releasedCheckRepo;

    @Transactional
    @Override
    public PostResponse releaseCheck(ReleasedCheque chequeToRelease, BindingResult bindingResult, MessageSource messageSource) {
        PostResponse response = new PostResponse();
        if (chequeToRelease != null) {
            CheckVoucherCheque check = chequeRepo.findOne(chequeToRelease.getCheck().getId());
            if (check != null && !check.isReleased()) {
                ReleasedCheque releasedCheque = releasedCheckRepo.save(chequeToRelease);

                if (releasedCheque != null) {
                    // set check as released
                    check.setReleased(true);
                    chequeRepo.save(check);

                    response.setModelId(releasedCheque.getId());
                    response.setSuccessMessage("Check successfully released!");
                    response.setSuccess(true);
                }

            }
        }
        return response;
    }
}
