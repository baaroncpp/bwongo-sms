package com.bwongo.core.merchant_mgt.api;

import com.bwongo.core.merchant_mgt.models.dto.request.MerchantApiSettingRequestDto;
import com.bwongo.core.merchant_mgt.models.dto.response.MerchantApiSettingResponseDto;
import com.bwongo.core.merchant_mgt.service.MerchantApiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/23/24
 * @LocalTime 2:02PM
 **/
@Tag(name = "Merchant Api Setting", description = "sms integration api setting configuration")
@RestController
@RequestMapping("/api/v1/merchant-api-configuration")
@RequiredArgsConstructor
public class MerchantApiSettingApi {

    private final MerchantApiService merchantApiService;

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.WRITE')")
    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public MerchantApiSettingResponseDto createMerchantApiSetting(@RequestBody MerchantApiSettingRequestDto merchantApiSettingRequestDto) {
        return merchantApiService.createMerchantApiSetting(merchantApiSettingRequestDto);
    }
}
