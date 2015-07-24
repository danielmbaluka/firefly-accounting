package com.tri.erp.spring.service.interfaces;

import net.sf.jasperreports.engine.JRDataSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by TSI Admin on 7/22/2015.
 */
public interface ReportsService extends Printable {

    @Transactional(readOnly = true)
    public HashMap reportParametersForRegister(String registerType, String from, String to, HttpServletRequest request);

    @Transactional(readOnly = true)
    public JRDataSource datasourceForRegister(String from, String to);
}
