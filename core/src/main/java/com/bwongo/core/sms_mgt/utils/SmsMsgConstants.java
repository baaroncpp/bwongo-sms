package com.bwongo.core.sms_mgt.utils;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/23/24
 * @LocalTime 8:56 PM
 **/
public class SmsMsgConstants {

    private SmsMsgConstants() { }

    public static final String NULL_MESSAGE = "message is required";
    public static final String NULL_SENDER = "sender is required";
    public static final String NULL_PHONE_NUMBER = "phoneNumber is required";
    public static final String EMPTY_SMS_REQUEST_LIST = "empty sms request list";
    public static final String SMS_NOT_FOUND = "sms : %s not found";
    public static final String SMS_ALREADY_DELIVERED = "sms already delivered";
    public static final String PENDING_DELIVERY = "pending delivery";
    public static final String NO_SMS_TRANSACTION_FOUND = "no sms transactions found";
    public static final String CANT_RESENT_TRANSACTION_FAILURE = "cannot resend transaction failure";
    public static final String SMS = "sms";
}
