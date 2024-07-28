package com.bwongo.core.base.model.enums;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 6/3/24
 * @LocalTime 6:59 PM
 **/
public enum CashFlowTypeEnum {
    MAIN_TO_BUSINESS("Money is leaving main wallet and going to user wallet"),
    STOCK_WITHDRAW("Money is leaving the system entirely"),
    BUSINESS_TO_MAIN("Money is leaving the user wallet and going to main wallet");

    private final String description;
    private CashFlowTypeEnum(String description){
        this.description = description;
    }

}
