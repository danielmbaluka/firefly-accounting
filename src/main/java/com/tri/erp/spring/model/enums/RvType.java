package com.tri.erp.spring.model.enums;

/**
 * Created by Personal on 5/19/2015.
 */
public enum RvType {

    FOR_PO(1, "For PO"),
    FOR_IT(2, "For IT"),
    FOR_REP(3, "For REP"),
    FOR_LAB(4, "For LAB");

    private int id;
    private String description;

    RvType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
