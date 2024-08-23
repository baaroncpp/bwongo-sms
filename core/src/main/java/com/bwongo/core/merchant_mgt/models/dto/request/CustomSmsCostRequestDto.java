package com.bwongo.core.merchant_mgt.models.dto.request;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;

import java.math.BigDecimal;

import static com.bwongo.core.merchant_mgt.utils.MerchantMsgConstants.SMS_COST_MUST_BE_GREATER_THAN_ZERO;
import static com.bwongo.core.merchant_mgt.utils.MerchantMsgConstants.SMS_COST_REQUIRED;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/23/24
 * @LocalTime 1:22 PM
 **/
public record CustomSmsCostRequestDto(
        Long merchantId,
        BigDecimal smsCost
) {
    public void validate() {
        Validate.notNull(smsCost, ExceptionType.BAD_REQUEST, SMS_COST_REQUIRED);
        Validate.isTrue((smsCost.compareTo(BigDecimal.ZERO) > 0), ExceptionType.BAD_REQUEST, SMS_COST_MUST_BE_GREATER_THAN_ZERO);
    }
}
