package com.bwongo.core.invoice.api;

import com.bwongo.core.base.model.dto.response.PageResponseDto;
import com.bwongo.core.invoice.models.dto.request.InvoiceRequestDto;
import com.bwongo.core.invoice.models.dto.response.InvoiceResponseDto;
import com.bwongo.core.invoice.service.InvoiceService;
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
 * @Date 9/29/24
 * @Time 10:32 AM
 **/
@Tag(name = "Account Api", description = "Manages manages invoices")
@RestController
@RequestMapping("/api/v1/invoice")
@RequiredArgsConstructor
public class InvoiceApi {

    private final InvoiceService invoiceService;

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.WRITE')")
    @PostMapping(path = "send", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public InvoiceResponseDto sendMerchantInvoice(@RequestBody InvoiceRequestDto invoiceRequestDto){
        return invoiceService.sendMerchantInvoice(invoiceRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.READ', 'ADMIN_ROLE.READ')")
    @GetMapping(path = "all", produces = APPLICATION_JSON)
    public PageResponseDto getAllInvoices(@RequestParam(name = "page") int page,
                                          @RequestParam(name = "size") int size){
        var pageable = PageRequest.of(page, size, Sort.by(CREATED_ON).descending());
        return invoiceService.getInvoices(pageable);
    }
}
