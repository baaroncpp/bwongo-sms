package com.bwongo.core.account_mgt.api;

import com.bwongo.core.account_mgt.models.dto.request.MomoDepositRequestDto;
import com.bwongo.core.account_mgt.models.dto.response.AccountResponseDto;
import com.bwongo.core.account_mgt.models.dto.response.MomoDepositResponseDto;
import com.bwongo.core.account_mgt.service.AccountService;
import com.bwongo.core.base.model.dto.response.PageResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.bwongo.core.base.utils.BaseMsgUtils.CREATED_ON;
import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/21/24
 * @LocalTime 11:10 AM
 **/
@Tag(name = "Account Api", description = "Manages manages account, deposits and account CRUD operations")
@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountApi {

    private final AccountService accountService;

    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.WRITE')")
    @PostMapping(path = "momo-deposit", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public MomoDepositResponseDto makeMomoDeposit(@RequestBody MomoDepositRequestDto momoDepositRequestDto) {
        return accountService.makeMomoDeposit(momoDepositRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.READ', 'ADMIN_ROLE.READ')")
    @GetMapping(path = "momo-deposits/merchant/{id}", produces = APPLICATION_JSON)
    public PageResponseDto getMerchantMomoDeposits(@PathVariable("id") Long merchantId,
                                                   @RequestParam(name = "page") int page,
                                                   @RequestParam(name = "size") int size){
        var pageable = PageRequest.of(page, size, Sort.by(CREATED_ON).descending());
        return accountService.getMerchantMomoDeposits(merchantId, pageable);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.READ')")
    @GetMapping(path = "momo-deposits", produces = APPLICATION_JSON)
    public PageResponseDto getMomoDeposits(@RequestParam(name = "page") int page,
                                           @RequestParam(name = "size") int size){
        var pageable = PageRequest.of(page, size, Sort.by("createdOn").descending());
        return accountService.getMomoDeposits(pageable);
    }

    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.READ', 'ADMIN_ROLE.READ')")
    @GetMapping(path = "transactions/merchant/{id}", produces = APPLICATION_JSON)
    public PageResponseDto getMerchantTransactions(@PathVariable("id") Long merchantId,
                                                   @RequestParam(name = "page") int page,
                                                   @RequestParam(name = "size") int size){
        var pageable = PageRequest.of(page, size, Sort.by(CREATED_ON).descending());
        return accountService.getMerchantTransactions(merchantId, pageable);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.READ')")
    @GetMapping(path = "transactions", produces = APPLICATION_JSON)
    public PageResponseDto getTransactions(@RequestParam(name = "page") int page,
                                           @RequestParam(name = "size") int size){
        var pageable = PageRequest.of(page, size, Sort.by(CREATED_ON).descending());
        return accountService.getAccountTransactions(pageable);
    }

    @PreAuthorize("hasAnyAuthority('MERCHANT_ROLE.READ')")
    @GetMapping(produces = APPLICATION_JSON)
    public AccountResponseDto getMerchantAccount(){
        return accountService.getMerchantAccount();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.READ')")
    @GetMapping(path = "all", produces = APPLICATION_JSON)
    public PageResponseDto getAccounts(@RequestParam(name = "page") int page,
                                       @RequestParam(name = "size") int size){
        var pageable = PageRequest.of(page, size, Sort.by(CREATED_ON).descending());
        return accountService.getAccounts(pageable);
    }
}
