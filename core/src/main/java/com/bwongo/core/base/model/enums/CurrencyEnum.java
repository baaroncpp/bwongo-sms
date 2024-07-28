package com.bwongo.core.base.model.enums;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/26/24
 * @LocalTime 9:18 PM
 **/
public enum CurrencyEnum {
    UGX("UG","Uganda Shillings"),
    TZS("TZ","Tanzania Shillings"),
    USD("US","US Dollar"),
    KSH("KE","Kenya Shillings");

    private final String countryCode;
    private final String description;

    CurrencyEnum(String countryCode, String description){
        this.countryCode = countryCode;
        this.description = description;
    }


    public CurrencyEnum getCurrencyByCountryCode(String countryCode){
        for(CurrencyEnum v : values()){
            if(v.countryCode.equalsIgnoreCase(countryCode)){
                return v;
            }
        }
        return null;
    }
}
