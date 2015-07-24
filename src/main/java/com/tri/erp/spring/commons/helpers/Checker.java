package com.tri.erp.spring.commons.helpers;

import com.tri.erp.spring.model.Transaction;

import java.util.Collection;

/**
 * Created by TSI Admin on 9/16/2014.
 */
public class Checker {

    public static boolean collectionIsEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean validTransaction(Transaction transaction) {
        return transaction != null && transaction.getId() != null && transaction.getId() > 0;
    }

    public static boolean isStringNullAndEmpty(String str) {
        return !(str != null && str.length() > 0);
    }
}
