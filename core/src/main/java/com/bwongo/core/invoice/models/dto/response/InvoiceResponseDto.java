package com.bwongo.core.invoice.models.dto.response;

import com.bwongo.core.base.model.enums.InvoiceStatusEnum;
import com.bwongo.core.merchant_mgt.models.dto.response.MerchantResponseDto;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 9/22/24
 * @Time 12:35 PM
 **/
public record InvoiceResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        String invoiceNumber,
        Date issuedOnDate,
        BigDecimal totalAmount,
        MerchantResponseDto merchant,
        InvoiceStatusEnum invoiceStatus,
        Date paymentDueDate,
        Long quantity
) {
}
