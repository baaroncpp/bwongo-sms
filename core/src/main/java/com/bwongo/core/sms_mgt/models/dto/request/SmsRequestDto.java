package com.bwongo.core.sms_mgt.models.dto.request;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;

import static com.bwongo.commons.text.StringRegExUtil.stringOfInternationalPhoneNumber;
import static com.bwongo.core.sms_mgt.utils.SmsMsgConstants.*;
import static com.bwongo.core.user_mgt.utils.UserMsgConstants.INVALID_PHONE_NUMBER;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/23/24
 * @LocalTime 8:54PM
 **/
public record SmsRequestDto(
        String phoneNumber,
        String message,
        String sender
) {
    public void validate(){
        Validate.notEmpty(phoneNumber, NULL_PHONE_NUMBER);
        stringOfInternationalPhoneNumber(phoneNumber, INVALID_PHONE_NUMBER);
        Validate.notEmpty(message, NULL_MESSAGE);
        Validate.notEmpty(sender, NULL_SENDER);
    }
}
