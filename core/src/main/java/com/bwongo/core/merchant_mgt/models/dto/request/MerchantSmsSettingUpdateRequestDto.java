package com.bwongo.core.merchant_mgt.models.dto.request;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;

import java.math.BigDecimal;

import static com.bwongo.core.base.utils.EnumValidation.isPaymentTypeEnum;
import static com.bwongo.core.merchant_mgt.utils.MerchantMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/14/24
 * @LocalTime 7:02AM
 **/
public record MerchantSmsSettingUpdateRequestDto(
        Long id,
        boolean isCustomized,
        String customizedTitle,
        String paymentType
) {
    public void validate(){
        Validate.notNull(id, ExceptionType.BAD_REQUEST, ID_REQUIRED);
        Validate.notNull(isCustomized, ExceptionType.BAD_REQUEST, IS_CUSTOMIZED_REQUIRED);
        Validate.notEmpty(paymentType, NULL_PAYMENT_TYPE);
        Validate.isTrue(isPaymentTypeEnum(paymentType), ExceptionType.BAD_REQUEST, INVALID_PAYMENT_TYPE);

        if(isCustomized)
            Validate.notEmpty(customizedTitle, CUSTOMIZED_TITLE_REQUIRED);
    }
}
