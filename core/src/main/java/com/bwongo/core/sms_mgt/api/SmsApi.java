package com.bwongo.core.sms_mgt.api;

import com.bwongo.core.base.model.dto.response.PageResponseDto;
import com.bwongo.core.sms_mgt.models.dto.request.BulkSmsRequestDto;
import com.bwongo.core.sms_mgt.models.dto.request.SmsRequestDto;
import com.bwongo.core.sms_mgt.models.dto.response.SmsResponseDto;
import com.bwongo.core.sms_mgt.service.SmsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bwongo.core.base.utils.BaseMsgUtils.CREATED_ON;
import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 9/29/24
 * @Time 8:47 AM
 **/
@Tag(name = "Sms Api", description = "Manages sending sms, single and bulk, resend")
@RestController
@RequestMapping("/api/v1/sms")
@RequiredArgsConstructor
public class SmsApi {

    private final SmsService smsService;

    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.WRITE')")
    @PostMapping(path = "send", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public SmsResponseDto sendSms(@RequestBody SmsRequestDto smsRequestDto){
        return smsService.sendSms(smsRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.WRITE')")
    @PostMapping(path = "send/bulk", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public List<SmsResponseDto> sendBulkSms(@RequestBody BulkSmsRequestDto bulkSmsRequestDto){
        return smsService.sendBulkSms(bulkSmsRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.WRITE')")
    @GetMapping(path = "{id}/resend", produces = APPLICATION_JSON)
    public SmsResponseDto resendSms(@PathVariable("id") Long id){
        return smsService.resendSms(id);
    }

    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.READ', 'ADMIN_ROLE.READ')")
    @GetMapping(path = "merchant/{id}", produces = APPLICATION_JSON)
    public PageResponseDto getMerchantSms(@PathVariable("id") Long id,
                                          @RequestParam(name = "page") int page,
                                          @RequestParam(name = "size") int size){
        var pageable = PageRequest.of(page, size, Sort.by(CREATED_ON).descending());
        return smsService.getSmsByMerchant(id, pageable);
    }

    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.READ')")
    @GetMapping(path = "pageable", produces = APPLICATION_JSON)
    public PageResponseDto getAllSms(@RequestParam(name = "page") int page,
                                     @RequestParam(name = "size") int size){
        var pageable = PageRequest.of(page, size, Sort.by(CREATED_ON).descending());
        return smsService.getAllSms(pageable);
    }
}
