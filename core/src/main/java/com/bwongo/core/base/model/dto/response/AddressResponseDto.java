package com.bwongo.core.base.model.dto.response;

import com.bwongo.core.user_mgt.models.dto.response.UserResponseDto;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/20/24
 * @LocalTime 4:00 PM
 **/
public record AddressResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        UserResponseDto createdBy,
        UserResponseDto modifiedBy,
        String street,
        String addressDescription,
        String townOrVillage,
        CountryResponseDto country,
        Double latitudeCoordinate,
        Double longitudeCoordinate
) {
}
