package com.bwongo.core.invoice.models.dto.request;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;

import static com.bwongo.core.invoice.utils.InvoiceMsgConstant.DUE_DATE_REQUIRED;
import static com.bwongo.core.merchant_mgt.utils.MerchantMsgConstants.MERCHANT_ID_REQUIRED;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 9/29/24
 * @Time 9:35 AM
 **/
public record InvoiceRequestDto(
        Long merchantId,
        String dueDate
) {
    public void validate(){
        Validate.notNull(merchantId, ExceptionType.BAD_REQUEST, MERCHANT_ID_REQUIRED);
        Validate.notEmpty(dueDate, DUE_DATE_REQUIRED);
    }
}
