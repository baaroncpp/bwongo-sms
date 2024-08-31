package com.bwongo.core.base.api;


import com.bwongo.commons.models.dto.NotificationDto;
import com.bwongo.core.base.model.dto.request.CountryRequestDto;
import com.bwongo.core.base.model.dto.response.CountryResponseDto;
import com.bwongo.core.base.service.BaseService;
import com.bwongo.core.base.service.KafkaMessagePublisher;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Base Api", description = "Manages countries and location")
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class BaseApi {

    private final BaseService baseService;
    private final KafkaMessagePublisher kafkaMessagePublisher;

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.READ')")
    @GetMapping(path = "test/kafka")
    public void testKafka() {
        var notificationDto = NotificationDto.builder()
                .externalReference("21421521354")
                .internalReference("vdsvasvsdf")
                .merchantCode("")
                .message("am coming")
                .sender("tester1")
                .recipient("tester2")
                .build();

        kafkaMessagePublisher.sendNotificationToTopic(notificationDto);
    }

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
