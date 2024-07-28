package com.bwongo.core.base.model.enums;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/27/24
 * @LocalTime 12:51 AM
 **/
public enum AmortizationEnum {
    EQUAL_INSTALLMENTS(""),
    EQUAL_PRINCIPAL_PAYMENTS("");

    private final String description;

    AmortizationEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
