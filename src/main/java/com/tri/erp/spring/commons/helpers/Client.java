package com.tri.erp.spring.commons.helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TSI Admin on 9/11/2014.
 */
public class Client {

    public static Map getClientInfo() {
        Map map = new HashMap();
        map.put("system_uname", "system_uname");
        map.put("hostname", "hostname");

        map.put("ip_address", "ip");

        map.put("mac_address", "mac");
        return map;
    }
}
