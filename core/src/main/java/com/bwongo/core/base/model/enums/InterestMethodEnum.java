package com.bwongo.core.base.model.enums;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/27/24
 * @LocalTime 12:53 AM
 **/
public enum InterestMethodEnum {
    FLAT("Flat"),
    DECLINING_BALANCE("Declining Balance");

    private final String description;

    InterestMethodEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
