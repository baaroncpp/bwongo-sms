package com.bwongo.core.base.service;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;
import com.bwongo.core.base.model.dto.request.CountryRequestDto;
import com.bwongo.core.base.model.dto.response.CountryResponseDto;
import com.bwongo.core.base.repository.TCountryRepository;
import com.bwongo.core.base.service.dto.BaseDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.bwongo.core.base.utils.BaseMsgUtils.*;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/12/24
 * @LocalTime 3:31 PM
 **/
@Service
@RequiredArgsConstructor
public class BaseService {

    private final TCountryRepository countryRepository;
    private final BaseDtoService baseDtoService;
    private final AuditService auditService;

    public CountryResponseDto addCountry(CountryRequestDto countryRequestDto){

        countryRequestDto.validate();
        var countryCode = countryRequestDto.countryCode();
        var name = countryRequestDto.name();

        Validate.isTrue(!countryRepository.existsByCountryCode(countryCode), ExceptionType.BAD_REQUEST, COUNTRY_CODE_ALREADY_EXISTS, countryCode);
        Validate.isTrue(!countryRepository.existsByName(name), ExceptionType.BAD_REQUEST, COUNTRY_ALREADY_EXISTS, name);

        var country = baseDtoService.dtoToCountry(countryRequestDto);
        auditService.stampLongEntity(country);

        return baseDtoService.countryToDto(countryRepository.save(country));
    }

    public CountryResponseDto updateCountry(Long countryId, CountryRequestDto countryRequestDto){

        countryRequestDto.validate();
        var countryCode = countryRequestDto.countryCode();
        var name = countryRequestDto.name();

        var existingCountry = countryRepository.findById(countryId);
        Validate.isPresent(existingCountry, COUNTRY_WITH_ID_NOT_FOUND, countryId);
        var country = existingCountry.get();

        if(!country.getName().equals(name))
            Validate.isTrue(!countryRepository.existsByName(name), ExceptionType.BAD_REQUEST, COUNTRY_ALREADY_EXISTS, name);

        if(!Objects.equals(country.getCountryCode(), countryCode))
            Validate.isTrue(!countryRepository.existsByCountryCode(countryCode), ExceptionType.BAD_REQUEST, COUNTRY_CODE_ALREADY_EXISTS, countryCode);

        country.setCountryCode(countryCode);
        country.setName(name);
        country.setIsoAlpha3(countryRequestDto.isoAlpha3());
        country.setIsoAlpha2(countryRequestDto.isoAlpha2());
        auditService.stampLongEntity(country);

        return baseDtoService.countryToDto(countryRepository.save(country));
    }

    public List<CountryResponseDto> getAllCountries(){
        return countryRepository.findAll().stream()
                .map(baseDtoService::countryToDto)
                .toList();
    }
}
