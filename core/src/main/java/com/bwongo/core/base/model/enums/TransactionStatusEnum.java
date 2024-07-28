package com.bwongo.core.base.model.enums;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 6/3/24
 * @LocalTime 6:57 PM
 **/
public enum TransactionStatusEnum {
    PENDING("Transaction has not been fully processed"),
    SUCCESSFUL("Transaction has been processed by all parties involved"),
    FAILED("Transaction processing has failed");

    private final String description;

    TransactionStatusEnum(String description){
        this.description = description;
    }

    public String getDescription(){
        return  this.description;
    }
}
