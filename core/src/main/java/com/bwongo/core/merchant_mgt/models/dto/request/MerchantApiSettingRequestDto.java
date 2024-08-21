package com.bwongo.core.merchant_mgt.models.dto.request;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;

import static com.bwongo.core.merchant_mgt.utils.MerchantMsgConstants.INVALID_NO_DAYS;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/14/24
 * @LocalTime 7:28AM
 **/
public record MerchantApiSettingRequestDto(
        int numberOfDays
) {
    public void validate() {
        Validate.isTrue(numberOfDays > 0, ExceptionType.BAD_REQUEST, INVALID_NO_DAYS);
    }
}
