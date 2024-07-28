package com.bwongo.core.base.model.enums;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 5/20/24
 * @LocalTime 2:03 PM
 **/
public enum AccountGroupingTypeEnum {
    SACCO("Grouping of all the sacco accounts and shares"),
    BRANCH("Grouping of all the branch accounts, including product accounts"),
    CLIENT("Grouping of all the client accounts and loan"),;

    final String description;

    AccountGroupingTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
