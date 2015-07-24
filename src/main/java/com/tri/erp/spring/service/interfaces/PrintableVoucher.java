package com.tri.erp.spring.service.interfaces;

import net.sf.jasperreports.engine.JRDataSource;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by TSI Admin on 5/12/2015.
 */
public interface PrintableVoucher extends Printable {

    @Transactional(readOnly = true)
    public HashMap reportParameters(Integer vid, HttpServletRequest request);

    @Transactional(readOnly = true)
    public JRDataSource datasource(Integer vid);
}
