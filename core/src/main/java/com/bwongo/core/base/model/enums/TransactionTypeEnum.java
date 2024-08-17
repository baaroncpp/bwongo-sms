package com.bwongo.core.base.model.enums;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 6/3/24
 * @LocalTime 6:56 PM
 **/
public enum TransactionTypeEnum {
    MOMO_DEPOSIT("depositing money from MOMO to account"),
    MOMO_WITHDRAW("withdrawing money from wallet to MOMO"),
    ACCOUNT_DEBIT("money leaving account"),
    ACCOUNT_CREDIT("money coming to account"),
    REFUND("refunding money from system account to MOMO");

    private final String description;

    TransactionTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}