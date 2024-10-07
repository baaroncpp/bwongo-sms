package com.bwongo.core.merchant_mgt.models.dto.request;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;

import static com.bwongo.core.merchant_mgt.utils.MerchantMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/6/24
 * @Time 8:41 AM
 **/
public record ActivationCodeRequestDto(
        Long merchantId,
        String activationCode
) {
    public void validate(){
        Validate.notNull(merchantId, ExceptionType.BAD_REQUEST, MERCHANT_ID_REQUIRED);
        Validate.notEmpty(activationCode, ACTIVATION_CODE_REQUIRED);
    }
}
