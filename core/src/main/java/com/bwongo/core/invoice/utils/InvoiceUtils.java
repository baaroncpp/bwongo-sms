package com.bwongo.core.invoice.utils;

import com.bwongo.commons.text.StringUtil;
import com.bwongo.core.invoice.models.dto.MonthItemsDto;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import com.bwongo.core.sms_mgt.models.jpa.TSms;

import java.text.DateFormatSymbols;
import java.util.*;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 9/22/24
 * @Time 1:57 PM
 **/
public class InvoiceUtils {

    private InvoiceUtils() { }

    public static String generateMerchantInvoiceNumber(TMerchant merchant){
        return merchant.getMerchantCode() + StringUtil.getRandom6DigitString();
    }

    public static List<MonthItemsDto> getMonthItems(List<TSms> smsList){

        var months = getUniqueMonths(smsList);
        var monthItemsList = new ArrayList<MonthItemsDto>();

        months.forEach(month -> {
            var numberOfItems = 0;

            for(TSms sms : smsList){
                Calendar cal = Calendar.getInstance();
                cal.setTime(sms.getCreatedOn());
                // Calendar month is 0-based, so we subtract 1 from the input month
                if(cal.get(Calendar.MONTH) == (month)){
                    numberOfItems = numberOfItems + 1;
                }
            }

            var monthItemsDto = MonthItemsDto.builder()
                    .monthName(new DateFormatSymbols().getMonths()[month])
                    .numberOfItems((long)numberOfItems)
                    .build();

            monthItemsList.add(monthItemsDto);
        });

        return monthItemsList;
    }

    private static List<Integer> getUniqueMonths(List<TSms> smsList){
        Set<Integer> uniqueMonths = new HashSet<>();
        var calendar = Calendar.getInstance();

        smsList.forEach(sms -> {
            calendar.setTime(sms.getCreatedOn());
            var month = calendar.get(Calendar.MONTH);
            uniqueMonths.add(month);
        });
        return uniqueMonths.stream().sorted().toList();
    }

    public static Date addDaysToDate(Date date, int numberOfDays){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, numberOfDays);

        return calendar.getTime();
    }
}