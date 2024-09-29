package com.bwongo.core.account_mgt.utils;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 8:19 PM
 **/
public class AccountMsgUtils {

    private AccountMsgUtils() { }

    public static final String NULL_DEPOSITOR_NAME = "depositorName is required";
    public static final String NULL_MSISDN = "msisdn is required";
    public static final String NULL_DEPOSIT_AMOUNT = "depositAmount is required";
    public static final String AMOUNT_MUST_BE_GREATER_THAN_ZERO = "amount must be greater than zero";
    public static final String ACCOUNT_ERROR = "account is %s";
    public static final String ACCOUNT_DELETED = "account is deleted";
    public static final String INSUFFICIENT_FUNDS = "insufficient funds";
    public static final String NOT_CREDIT_ACCOUNT = "not a credit account";
    public static final String MERCHANT_ACCOUNT_NOT_FOUND = "%s account for merchant: %s not found";
}
