package com.bwongo.core.base.model.enums;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 5/28/24
 * @LocalTime 7:43 PM
 **/
public enum DaysInYearEnum {
    DAYS_360(360),
    DAYS_365(365);

    int daysInYear;

    DaysInYearEnum(int daysInYear) {
        this.daysInYear = daysInYear;
    }

    void setDaysInYear(int daysInYear) {
        this.daysInYear = daysInYear;
    }


}
