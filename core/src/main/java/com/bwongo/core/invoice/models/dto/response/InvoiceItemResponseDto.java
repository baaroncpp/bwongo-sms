package com.bwongo.core.invoice.models.dto.response;

import com.bwongo.core.invoice.models.jpa.TInvoice;
import com.bwongo.core.sms_mgt.models.jpa.TSms;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 9/22/24
 * @Time 12:37 PM
 **/
public record InvoiceItemResponseDto(
        Long id,
        Date createdOn,
        Date modifiedOn,
        TInvoice invoice,
        TSms sms
) {
}
