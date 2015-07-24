package com.tri.erp.spring.service.interfaces;

import net.sf.jasperreports.engine.JRDataSource;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Created by TSI Admin on 5/12/2015.
 */
public interface Bir2307 extends Printable {

    @Transactional(readOnly = true)
    public void fillPdf(Integer transId, Integer payeeAccountNo, String token, HttpServletResponse response);

}
