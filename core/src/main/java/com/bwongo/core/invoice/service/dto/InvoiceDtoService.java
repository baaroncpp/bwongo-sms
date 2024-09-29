package com.bwongo.core.invoice.service.dto;

import com.bwongo.core.invoice.models.dto.response.InvoiceResponseDto;
import com.bwongo.core.invoice.models.jpa.TInvoice;
import com.bwongo.core.merchant_mgt.service.dto.MerchantDtoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 9/22/24
 * @Time 12:33 PM
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceDtoService {

    private final MerchantDtoService merchantDtoService;


    public InvoiceResponseDto invoiceToDto(TInvoice invoice){

        if(invoice == null)
            return null;

        return new InvoiceResponseDto(
                invoice.getId(),
                invoice.getCreatedOn(),
                invoice.getModifiedOn(),
                invoice.getInvoiceNumber(),
                invoice.getIssuedOnDate(),
                invoice.getTotalAmount(),
                merchantDtoService.merchantToDto(invoice.getMerchant()),
                invoice.getInvoiceStatus(),
                invoice.getPaymentDueDate(),
                invoice.getQuantity()
        );
    }
}
