package com.tri.erp.spring.commons.helpers;

import com.tri.erp.spring.commons.GlobalConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by TSI Admin on 10/7/2014.
 */
public class ReportUtil {
    public static HashMap setupSharedReportHeaders(HttpServletRequest request) {

        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("COMP_NAME", "ILOILO I ELECTRIC COOPERATIVE, INC");
        parameters.put("COMP_ADDR", "Brgy. Namocon, Tigbauan Iloilo, Philippines 5021");
        parameters.put("COMP_CONTACT", "Tel.No.: (033) 511-81-38 * Email: ileco1mainoffice@gmail.com");
        parameters.put("LOGO_PATH", request.getSession().getServletContext().getRealPath("/resources/images/"));

        return parameters;
    }

    public static HashMap setupSharedReportHeaders() {

        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("COMP_NAME", "Your name");
        parameters.put("COMP_ADDRESS", "Your address");
        parameters.put("COMP_CONTACT", "Your contact");

        return parameters;
    }
}
