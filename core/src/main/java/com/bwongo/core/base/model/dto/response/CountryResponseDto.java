package com.bwongo.core.base.model.dto.response;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/12/24
 * @LocalTime 3:36 PM
 **/
public record CountryResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        String name,
        String isoAlpha2,
        String isoAlpha3,
        Integer countryCode
) {
}
