package com.bwongo.core.base.api;

import com.bwongo.core.base.model.dto.request.CountryRequestDto;
import com.bwongo.core.base.model.dto.response.CountryResponseDto;
import com.bwongo.core.base.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author bkaaron
 * @Date 3/12/24
 * @LocalTime 3:38 PM
 **/
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class BaseApi {

    private final BaseService baseService;

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.READ')")
    @PostMapping(path = "country", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CountryResponseDto addCountry(@RequestBody CountryRequestDto countryRequestDto){
        return baseService.addCountry(countryRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.UPDATE')")
    @PutMapping(path = "country/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CountryResponseDto updateCountry(@RequestBody CountryRequestDto countryRequestDto,
                                            @PathVariable("id") Long id){
        return baseService.updateCountry(id, countryRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.READ')")
    @GetMapping(path = "countries", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CountryResponseDto> getAllCountries(){
        return baseService.getAllCountries();
    }

}
