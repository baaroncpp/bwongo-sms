package com.bwongo.core.base.service.dto;

import com.bwongo.core.base.model.dto.request.CountryRequestDto;
import com.bwongo.core.base.model.dto.response.CountryResponseDto;
import com.bwongo.core.base.model.jpa.TCountry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/12/24
 * @LocalTime 3:35 PM
 **/
@Service
@RequiredArgsConstructor
public class BaseDtoService {

    public CountryResponseDto countryToDto(TCountry country){

        if(country == null){
            return null;
        }

        return new CountryResponseDto(
                country.getId(),
                country.getCreatedOn(),
                country.getModifiedOn(),
                country.getName(),
                country.getIsoAlpha2(),
                country.getIsoAlpha3(),
                country.getCountryCode()
        );
    }

    public TCountry dtoToCountry(CountryRequestDto countryRequestDto){

        if(countryRequestDto == null){
            return null;
        }

        var country = new TCountry();
        country.setCountryCode(countryRequestDto.countryCode());
        country.setName(countryRequestDto.name());
        country.setIsoAlpha2(countryRequestDto.isoAlpha2());
        country.setIsoAlpha3(countryRequestDto.isoAlpha3());

        return country;
    }
}
