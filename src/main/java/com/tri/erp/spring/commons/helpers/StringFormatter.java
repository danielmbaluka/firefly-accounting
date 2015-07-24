package com.tri.erp.spring.commons.helpers;

import com.tri.erp.spring.commons.GlobalConstant;

/**
 * Created by TSI Admin on 9/11/2014.
 */
public class StringFormatter {

    public static String ucFirst(String text) {
        if (text.trim().length() == 0) {
            return text;
        } else {
            return text.substring(0, 1).toUpperCase() + text.substring(1);
        }
    }

    public static String buildAccountCode(String accountTypeCode, String groupCode,
                                          String GLAccount, String SLAccount,
                                          String auxAccount) {

        String dash = "-";
        String accountCode = accountTypeCode + groupCode + dash + GLAccount + dash
                + SLAccount + dash + auxAccount;
        return accountCode;
    }

    public static String removeBaseFromRoute(String route) {
        if (route != null) {
            int startIndex = route.indexOf("#") + 1;
            return route.substring(startIndex, route.length());
        } else {
            return "";
        }
    }

    public static String replaceRouteParamWithPlaceholder(String uri, String... params) {
        if (params.length > 0) {
            for (String p : params) {
                uri = uri.replace(p, GlobalConstant.ROUTE_PARAM_PLACEHOLDER);
            }
        }
        return uri;
    }

    public static String[] breakTIN(String tin) {
        String[] tinArr = {"", "", "", ""};
        if (tin != null && tin.length() == 12) {
            int len = tin.length();
            tinArr[0] = tin.substring(0, 3);
            tinArr[1] = tin.substring(3, 6);
            tinArr[2] = tin.substring(6, 9);
            tinArr[3] = tin.substring(9, 12);
        }
        return tinArr;
    }
}
