package com.bwongo.core.base.utils;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/12/24
 * @LocalTime 3:43 PM
 **/
public class BaseMsgUtils {

    private BaseMsgUtils() { }

    public static final String CREATED_ON = "createdOn";
    public static final String COUNTRY_NAME_REQUIRED = "Country name is required";
    public static final String COUNTRY_ISO_ALPHA2_REQUIRED = "Country isoAlpha2 is required";
    public static final String COUNTRY_ISO_ALPHA3_REQUIRED = "Country isoAlpha3 is required";
    public static final String COUNTRY_CODE_REQUIRED = "Country code or numeric code is required";
    public static final String DISTRICT_NAME_REQUIRED = "District name is required";
    public static final String DISTRICT_REGION_REQUIRED = "District region is required";
    public static final String COUNTRY_ID_REQUIRED = "Country ID is required";
    public static final String COUNTRY_WITH_ID_NOT_FOUND = "Country with ID %s not found";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String VALUE_TOO_LONG = "%s is too long, cannot exceed %s characters";
    public static final String COUNTRY_CODE_ALREADY_EXISTS = "countryCode %s already exists";
    public static final String COUNTRY_ALREADY_EXISTS = "country %s already exists";
    public static final String DISTRICT_ALREADY_EXISTS_COUNTRY = "district %s already exists in %s";
    public static final String DISTRICT_NOT_FOUND = "district with %s not found";
    public static final String ACCEPTABLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
