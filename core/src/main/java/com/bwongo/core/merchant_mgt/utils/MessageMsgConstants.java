package com.bwongo.core.merchant_mgt.utils;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 7/31/24
 * @LocalTime 1:12 PM
 **/
public class MessageMsgConstants {

    private MessageMsgConstants() {}

    public static final String NULL_COUNTRY_ID = "countryId is required";
    public static final String MERCHANT_NAME_REQUIRED = "MerchantName is required";
    public static final String LOCATION_REQUIRED = "Location is required";
    public static final String MERCHANT_TYPE_REQUIRED = "MerchantType is required";
    public static final String INVALID_MERCHANT_TYPE = "Invalid merchantType";
    public static final String MERCHANT_ID_REQUIRED = "MerchantId is required";
    public static final String SMS_COST_REQUIRED = "smsCost is required";
    public static final String IS_CUSTOMIZED_REQUIRED = "isCustomized is required";
    public static final String CUSTOMIZED_TITLE_REQUIRED = "customizedTitle is required";
    public static final String SMS_COST_MUST_BE_GREATER_THAN_ZERO = "smsCost must be greater than zero";
    public static final String MERCHANT_NOT_FOUND = "Merchant with ID: %s not found";
    public static final String MERCHANT_ALREADY_ACTIVATED = "Merchant with ID: %s is already activated";
    public static final String CONTACT_ADMIN_MAX_FAILS = "Contact admin maximum number of fails reached";
    public static final String MERCHANT_ACTIVATION_NOT_FOUND = "MerchantActivation with code: %s not found";
    public static final String INVALID_ACTIVATION_CODE = "Invalid activation code: %s";
}
