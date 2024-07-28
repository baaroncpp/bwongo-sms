package com.bwongo.core.base.model.enums;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/26/24
 * @LocalTime 10:56 PM
 **/
public enum ChargeTypeEnum {
    SMS(3),

    ACTIVATION(2),
    ANNUAL(2),
    MONTHLY(2),
    WEEKLY(2),
    WITHDRAWAL(2),

    LOAN_DISBURSEMENT(1),
    LOAN_INSTALLMENT(1),
    LOAN_OVERDUE(1);

    final int value;

    ChargeTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
