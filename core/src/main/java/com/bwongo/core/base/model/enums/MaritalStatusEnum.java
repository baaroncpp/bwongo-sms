package com.bwongo.core.base.model.enums;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 4/13/24
 * @LocalTime 3:26 PM
 **/
public enum MaritalStatusEnum {
    WIDOWED("persons who have lost their legally-married spouse"),
    DIVORCED("persons who have obtained a legal divorce and have not remarried"),
    MARRIED("persons who are legally or traditionally married"),
    SEPARATED("persons currently legally married but who are no longer living with their spouse"),
    SINGLE("persons who have never married and persons whose marriage has been legally annulled");

    private final String description;

    MaritalStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
