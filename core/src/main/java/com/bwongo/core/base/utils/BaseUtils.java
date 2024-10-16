package com.bwongo.core.base.utils;

import com.bwongo.commons.utils.DateTimeUtil;
import com.bwongo.commons.utils.Validate;
import com.bwongo.core.base.model.dto.response.PageResponseDto;
import com.bwongo.core.base.model.jpa.TNotification;
import com.google.gson.Gson;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 4/7/24
 * @LocalTime 1:04 PM
 **/
public class BaseUtils {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private BaseUtils() { }

    public static Date getDateFromString(String stringDate){
        Validate.isAcceptableDateFormat(stringDate);
        return DateTimeUtil.stringToDate(stringDate, DATE_TIME_FORMAT);
    }

    public static PageResponseDto pageToDto(Page<?> page, List<?> data) {

        var totalPages = page.getTotalPages();
        var number = page.getNumber();
        var size = page.getSize();
        var totalElements = page.getTotalElements();

        return PageResponseDto.builder()
                .pageNumber(number)
                .pageSize(size)
                .totalElementsCount(totalElements)
                .totalPages(totalPages)
                .data(data)
                .build();
    }

    public static String convertNotificationToJson(TNotification notification){
        return new Gson().toJson(notification);
    }
}
