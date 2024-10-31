package com.bwongo.sms_integration_service.security.network;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/29/24
 * @Time 10:45PM
 **/
@HttpExchange
public interface MerchantSecurityClient {

    @GetExchange("api/v1/auth/validate/merchant/code/{code}/secret-key/{secretKey}")
    public boolean validateMerchantKeyValue(@PathVariable("secretKey") String secretKey, @PathVariable("code")String merchantCode);
}
