package com.bwongo.core.merchant_mgt.api;

import com.bwongo.core.base.model.dto.response.PageResponseDto;
import com.bwongo.core.merchant_mgt.models.dto.request.MerchantRequestDto;
import com.bwongo.core.merchant_mgt.models.dto.request.MerchantUpdateRequestDto;
import com.bwongo.core.merchant_mgt.models.dto.response.MerchantResponseDto;
import com.bwongo.core.merchant_mgt.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/9/24
 * @LocalTime 10:34 AM
 **/
@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantApi {

    private final MerchantService merchantService;

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
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

    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.READ','ADMIN_ROLE.READ')")
    @GetMapping(path = "all", produces = APPLICATION_JSON)
    public PageResponseDto getAllMerchants(@RequestParam(name = "page") int page,
                                           @RequestParam(name = "size") int size){
        var pageable = PageRequest.of(page, size, Sort.by("createdOn").descending());
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
    @GetMapping(path = "activation-code/{code}")
    public MerchantResponseDto activateMerchantByCode(@PathVariable("code") String code){
        return merchantService.activateMerchantByCode(code);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.WRITE')")
    @GetMapping(path = "{id}/activate")
    public MerchantResponseDto activateMerchantByAdmin(@PathVariable("id") Long id){
        return merchantService.activateMerchantByAdmin(id);
    }
}
