package com.bwongo.core.sms_mgt.models.dto.request;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;

import java.util.List;

import static com.bwongo.core.sms_mgt.utils.SmsMsgConstants.EMPTY_SMS_REQUEST_LIST;
import static com.bwongo.core.sms_mgt.utils.SmsMsgConstants.NULL_SENDER;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/23/24
 * @LocalTime 9:03 PM
 **/
public record BulkSmsRequestDto(
        String sender,
        List<SmsDto> smsDtoList
) {
    public void validate(){
        Validate.notEmpty(sender, NULL_SENDER);
        Validate.isTrue(!smsDtoList.isEmpty(), ExceptionType.BAD_REQUEST, EMPTY_SMS_REQUEST_LIST);
        smsDtoList.forEach(SmsDto::validate);
    }
}
