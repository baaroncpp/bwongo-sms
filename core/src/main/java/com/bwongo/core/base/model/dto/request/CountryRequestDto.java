package com.bwongo.core.base.model.dto.request;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;

import static com.bwongo.core.base.utils.BaseMsgUtils.*;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/12/24
 * @LocalTime 3:46 PM
 **/
public record CountryRequestDto(
        String name,
        String isoAlpha2,
        String isoAlpha3,
        Integer countryCode
) {
    public void validate(){
        Validate.notEmpty(name, COUNTRY_NAME_REQUIRED);
        Validate.notEmpty(isoAlpha2, COUNTRY_ISO_ALPHA2_REQUIRED);
        Validate.notEmpty(name, COUNTRY_ISO_ALPHA3_REQUIRED);
        Validate.notNull(countryCode, ExceptionType.BAD_REQUEST, COUNTRY_CODE_REQUIRED);
    }
}
