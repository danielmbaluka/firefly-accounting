package com.tri.erp.spring.service.interfaces;

import net.sf.jasperreports.engine.JRDataSource;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by TSI Admin on 5/12/2015.
 */
public interface PrintableCheque extends PrintableVoucher {

    @Transactional(readOnly = true)
    public HashMap reportParameters(Integer transId, Integer bankAccountId);

}
