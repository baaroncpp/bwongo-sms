package com.bwongo.core.invoice.models.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 9/22/24
 * @Time 3:49 PM
 **/
@Data
@Builder
public class MonthItemsDto {
    private String monthName;
    private Long numberOfItems;
}
