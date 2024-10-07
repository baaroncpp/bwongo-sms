package com.bwongo.core.merchant_mgt.api;

import com.bwongo.core.base.model.dto.response.PageResponseDto;
import com.bwongo.core.merchant_mgt.models.dto.request.*;
import com.bwongo.core.merchant_mgt.models.dto.response.MerchantResponseDto;
import com.bwongo.core.merchant_mgt.models.dto.response.MerchantSmsSettingResponseDto;
import com.bwongo.core.merchant_mgt.service.MerchantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.bwongo.core.base.utils.BaseMsgUtils.CREATED_ON;
import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/9/24
 * @LocalTime 10:34 AM
 **/
@Tag(name = "Merchant Api", description = "Manages manages merchant and merchant configuration CRUD operations")
@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantApi {

    private final MerchantService merchantService;

    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(path = "create", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public MerchantResponseDto addMerchant(@RequestBody MerchantRequestDto merchantRequestDto){
        return merchantService.addMerchant(merchantRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.UPDATE','ADMIN_ROLE.UPDATE')")
    @PutMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public MerchantResponseDto updateMerchant(@RequestBody MerchantUpdateRequestDto merchantUpdateRequestDto){
        return merchantService.updateMerchant(merchantUpdateRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.READ','ADMIN_ROLE.READ')")
    @GetMapping(path = "{id}", produces = APPLICATION_JSON)
    public MerchantResponseDto getMerchant(@PathVariable("id") Long id){
        return merchantService.getMerchant(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.READ')")
    @GetMapping(path = "pageable", produces = APPLICATION_JSON)
    public PageResponseDto getAllMerchants(@RequestParam(name = "page") int page,
                                           @RequestParam(name = "size") int size){
        var pageable = PageRequest.of(page, size, Sort.by(CREATED_ON).descending());
        return merchantService.getAll(pageable);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @GetMapping(path = "{id}/resend/code")
    public void resendActivationCode(@PathVariable("id") Long id){
        merchantService.resendActivationCode(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(path = "activate", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public MerchantResponseDto activateMerchantByCode(@RequestBody ActivationCodeRequestDto activationCodeRequestDto){
        return merchantService.activateMerchantByCode(activationCodeRequestDto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.WRITE')")
    @GetMapping(path = "{id}/activate", produces = APPLICATION_JSON)
    public MerchantResponseDto activateMerchantByAdmin(@PathVariable("id") Long id){
        return merchantService.activateMerchantByAdmin(id);
    }

    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(path = "sms-setting", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public MerchantSmsSettingResponseDto addSmsSetting(@RequestBody MerchantSmsSettingRequestDto merchantSmsSettingRequestDto){
        return merchantService.addMerchantSmsSetting(merchantSmsSettingRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.WRITE','ADMIN_ROLE.UPDATE')")
    @PutMapping(path = "sms-setting", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public MerchantSmsSettingResponseDto updateSmsSetting(@RequestBody MerchantSmsSettingUpdateRequestDto merchantSmsSettingUpdateRequestDto){
        return merchantService.updateMerchantSmsSetting(merchantSmsSettingUpdateRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.UPDATE')")
    @PatchMapping(path = "customize/sms-cost", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public MerchantSmsSettingResponseDto addCustomSms(@RequestBody CustomSmsCostRequestDto customSmsCostRequestDto){
        return merchantService.setCustomSmsCost(customSmsCostRequestDto);
    }
}
