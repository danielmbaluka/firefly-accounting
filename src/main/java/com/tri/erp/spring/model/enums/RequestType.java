package com.tri.erp.spring.model.enums;

/**
 * Created by Ryan D. Repe on 10/6/2014.
 */
public enum RequestType {
    MCT(1),
    MR(2),
    OTHERS(3)
    ;
    private int id;

    RequestType(int id) {
        this.id = id;
    }

    public static RequestType parse(int id) {
        RequestType type = null; // Default
        for (RequestType item : RequestType.values()) {
            if (item.getId()==id) {
                type = item;
                break;
            }
        }
        return type;
    }

    public int getId() {
        return id;
    }
}
