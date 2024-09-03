package com.bwongo.core.sms_mgt.utils;

import com.bwongo.commons.text.StringUtil;

import java.util.function.Function;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 9/4/24
 * @LocalTime 12:07AM
 **/
public class SmsUtils {

    private SmsUtils() {}

    public static Function<String, String> getInternalReference = merchantCode -> merchantCode + StringUtil.getRandom6DigitString();
}
