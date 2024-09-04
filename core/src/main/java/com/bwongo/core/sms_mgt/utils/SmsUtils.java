package com.bwongo.core.sms_mgt.utils;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.text.StringUtil;
import com.bwongo.commons.utils.Validate;
import com.bwongo.core.base.model.enums.SmsStatusEnum;
import com.bwongo.core.sms_mgt.models.jpa.TSms;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.bwongo.core.sms_mgt.utils.SmsMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 9/4/24
 * @LocalTime 12:07AM
 **/
public class SmsUtils {

    private SmsUtils() {}

    public static Function<String, String> getInternalReference = merchantCode -> merchantCode + StringUtil.getRandom6DigitString();

    public static Consumer<TSms> checkIfSmsCanBeResent = sms -> {
        Validate.isTrue(!sms.getSmsStatus().equals(SmsStatusEnum.DELIVERED), ExceptionType.BAD_REQUEST, SMS_ALREADY_DELIVERED);
        Validate.notNull(sms.getExternalReference(), ExceptionType.BAD_REQUEST, PENDING_DELIVERY);
    };
}
