package com.tri.erp.spring.commons;

import com.tri.erp.spring.commons.helpers.Client;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by TSI Admin on 9/11/2014.
 */

public class Debug {

    public static void printAndQuit(ArrayList<Object> objs)
    {
        StringBuffer stringBuffer = new StringBuffer();
        for (Object object : objs) {
            String val = String.valueOf(object);
            stringBuffer.append(val);
            stringBuffer.append("\n");
        }
        System.out.println(stringBuffer.toString());
        System.exit(0);
    }

    public static void printAndQuit(Object obj)
    {
        System.out.println(String.valueOf(obj));
        System.exit(0);
    }

    public static void print(Object obj)
    {
        System.out.println(String.valueOf(obj));
    }
}