package com.tri.erp.spring.model.enums;

/**
 * Created by Ryan D. Repe on 10/13/2014.
 */
public enum DocumentStatus {

    DOCUMENT_CREATED(1),
    FOR_CHECKING(2),
    FOR_RECOMMENDATION(3),
    FOR_AUDIT(4),
    FOR_APPROVAL(5),
    FOR_CHECK_WRITING(6),
    APPROVED(7),
    DENIED(8),
    FOR_CANVASSING(9),
    CLOSED(10),
    RETURNED_TO_CREATOR(11),
    RETURNED_TO_CANVASSING(12),
    RETURNED_TO_AUDIT(13),
    DELETED(14),
    RETURNED_TO_ACCOUNTANT(15),
    RETURNED_TO_CASHIER(16),
    CHECKED(17),
    FOR_RECEIVING(18),
    FOR_SPECIAL_EQUIPMENT(19),
    FOR_REPAIR(20),
    REPAIRED(21),
    RECEIVED(22),
    RETURNED_FROM_GM(23),
    FOR_RELEASING(24),
    RELEASED(25),
    CANCELLED(26),
    APPROVED_FOR_INSTALLMENT(27),
    FOR_APPROVAL_OF_GM(28);

    private int id;

    DocumentStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}