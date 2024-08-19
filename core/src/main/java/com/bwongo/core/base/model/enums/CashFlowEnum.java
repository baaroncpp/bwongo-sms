package com.bwongo.core.base.model.enums;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 2:24 PM
 **/
public enum CashFlowEnum {
    MAIN_TO_BUSINESS("Money is leaving main account and going to user account"),
    STOCK_WITHDRAW("Money is leaving the system entirely"),
    BUSINESS_TO_MAIN("Money is leaving the user account and going to main account");

    private final String description;

    CashFlowEnum(String description) {
        this.description = description;
    }
}
