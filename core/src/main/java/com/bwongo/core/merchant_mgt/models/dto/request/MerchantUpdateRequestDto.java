package com.bwongo.core.merchant_mgt.models.dto.request;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.text.StringRegExUtil;
import com.bwongo.commons.utils.Validate;
import com.bwongo.core.base.model.enums.MerchantTypeEnum;

import static com.bwongo.core.base.utils.EnumValidation.isMerchantTypeEnum;
import static com.bwongo.core.merchant_mgt.utils.MessageMsgConstants.*;
import static com.bwongo.core.user_mgt.utils.UserMsgConstants.*;
import static com.bwongo.core.user_mgt.utils.UserMsgConstants.INVALID_PHONE_NUMBER;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/9/24
 * @LocalTime 9:28 AM
 **/
public record MerchantUpdateRequestDto(
        Long merchantId,
        Long countryId,
        String email,
        String phoneNumber,
        String merchantName,
        String location,
        String merchantType
) {
    public void validate(){
        Validate.notNull(merchantId, ExceptionType.BAD_REQUEST, MERCHANT_ID_REQUIRED);
        Validate.notNull(countryId, ExceptionType.BAD_REQUEST, NULL_COUNTRY_ID);
        Validate.notEmpty(email, EMAIL_REQUIRED);
        StringRegExUtil.stringOfEmail(email, INVALID_EMAIL);
        Validate.notEmpty(phoneNumber, PHONE_NUMBER_REQUIRED);
        StringRegExUtil.stringOfInternationalPhoneNumber(phoneNumber, INVALID_PHONE_NUMBER);
        Validate.notEmpty(merchantName, MERCHANT_NAME_REQUIRED);
        Validate.notEmpty(merchantType, MERCHANT_TYPE_REQUIRED);
        Validate.isTrue(isMerchantTypeEnum(merchantType), ExceptionType.BAD_REQUEST, INVALID_MERCHANT_TYPE);

        if(merchantType.equals(MerchantTypeEnum.BUSINESS) || merchantType.equals(MerchantTypeEnum.COMPANY) || merchantType.equals(MerchantTypeEnum.ORGANIZATION))
            Validate.notEmpty(location, LOCATION_REQUIRED);
    }
}
