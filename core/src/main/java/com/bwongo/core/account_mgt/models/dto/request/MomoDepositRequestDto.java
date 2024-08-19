package com.bwongo.core.account_mgt.models.dto.request;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;

import java.math.BigDecimal;

import static com.bwongo.commons.text.StringRegExUtil.stringOfInternationalPhoneNumber;
import static com.bwongo.core.account_mgt.utils.AccountMsgUtils.*;
import static com.bwongo.core.user_mgt.utils.UserMsgConstants.INVALID_PHONE_NUMBER;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/19/24
 * @LocalTime 8:13 PM
 **/
public record MomoDepositRequestDto(
        String msisdn,
        String depositorName,
        BigDecimal amountDeposit
) {
    public void validate(){
        Validate.notEmpty(depositorName, NULL_DEPOSITOR_NAME);
        Validate.notEmpty(msisdn, NULL_MSISDN);
        Validate.notNull(amountDeposit, ExceptionType.BAD_REQUEST, NULL_DEPOSIT_AMOUNT);
        stringOfInternationalPhoneNumber(msisdn, INVALID_PHONE_NUMBER);
        Validate.isTrue((amountDeposit.compareTo(BigDecimal.ZERO) > 0), ExceptionType.BAD_REQUEST, AMOUNT_MUST_BE_GREATER_THAN_ZERO);
    }
}
