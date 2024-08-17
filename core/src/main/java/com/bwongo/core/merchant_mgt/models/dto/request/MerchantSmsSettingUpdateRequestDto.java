package com.bwongo.core.merchant_mgt.models.dto.request;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;

import java.math.BigDecimal;

import static com.bwongo.core.merchant_mgt.utils.MessageMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/14/24
 * @LocalTime 7:02AM
 **/
public record MerchantSmsSettingUpdateRequestDto(
        Long id,
        BigDecimal smsCost,
        boolean isCustomized,
        String customizedTitle
) {
    public void validate(){
        Validate.notNull(id, ExceptionType.BAD_REQUEST, ID_REQUIRED);
        Validate.notNull(smsCost, ExceptionType.BAD_REQUEST, SMS_COST_REQUIRED);
        Validate.notNull(isCustomized, ExceptionType.BAD_REQUEST, IS_CUSTOMIZED_REQUIRED);
        Validate.isTrue((smsCost.compareTo(BigDecimal.ZERO) > 0), ExceptionType.BAD_REQUEST, SMS_COST_MUST_BE_GREATER_THAN_ZERO);

        if(isCustomized)
            Validate.notEmpty(customizedTitle, CUSTOMIZED_TITLE_REQUIRED);
    }
}
