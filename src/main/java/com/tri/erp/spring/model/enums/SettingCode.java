package com.tri.erp.spring.model.enums;

/**
 * Created by Ryan D. Repe on 10/13/2014.
 */
public enum SettingCode {

    WTAX_ACCOUNT("WTAX_ACCOUNT");

    private String code;

    SettingCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}