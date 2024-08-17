package com.bwongo.core.merchant_mgt.models.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/14/24
 * @LocalTime 11:05 AM
 **/
@Data
@Builder
public class ApiKeyAndSecretDto {
    private String apiKey;
    private String secret;
}
