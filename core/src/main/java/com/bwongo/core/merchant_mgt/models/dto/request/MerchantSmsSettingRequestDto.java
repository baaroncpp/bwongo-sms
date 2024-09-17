package com.bwongo.core.merchant_mgt.models.dto.request;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;

import java.math.BigDecimal;

import static com.bwongo.core.base.utils.EnumValidation.isPaymentTypeEnum;
import static com.bwongo.core.merchant_mgt.utils.MerchantMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 7/31/24
 * @LocalTime 1:49 PM
 **/
public record MerchantSmsSettingRequestDto(
        Long merchantId,
        //BigDecimal smsCost,
        boolean isCustomized,
        String customizedTitle,
        String paymentType
) {
    public void validate(){
        Validate.notNull(merchantId, ExceptionType.BAD_REQUEST, MERCHANT_ID_REQUIRED);
        //Validate.notNull(smsCost, ExceptionType.BAD_REQUEST, SMS_COST_REQUIRED);
        Validate.notNull(isCustomized, ExceptionType.BAD_REQUEST, IS_CUSTOMIZED_REQUIRED);
        //Validate.isTrue((smsCost.compareTo(BigDecimal.ZERO) > 0), ExceptionType.BAD_REQUEST, SMS_COST_MUST_BE_GREATER_THAN_ZERO);
        Validate.notEmpty(paymentType, NULL_PAYMENT_TYPE);
        Validate.isTrue(isPaymentTypeEnum(paymentType), ExceptionType.BAD_REQUEST, INVALID_PAYMENT_TYPE);

        if(isCustomized)
            Validate.notEmpty(customizedTitle, CUSTOMIZED_TITLE_REQUIRED);
    }
}
