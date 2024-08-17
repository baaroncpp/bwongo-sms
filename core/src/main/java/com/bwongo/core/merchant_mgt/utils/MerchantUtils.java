package com.bwongo.core.merchant_mgt.utils;

import com.bwongo.commons.utils.DateTimeUtil;
import com.bwongo.core.merchant_mgt.models.dto.ApiKeyAndSecretDto;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import org.jasypt.util.text.BasicTextEncryptor;

import java.util.UUID;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 8/14/24
 * @LocalTime 10:43AM
 **/
public class MerchantUtils {

    private MerchantUtils() {}

    public static String generateApiKey(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 20).toUpperCase();
    }

    public static ApiKeyAndSecretDto generateApiSecretAndSecret(TMerchant merchant, String encryptionPassword){
        var apiKey = generateApiKey();
        var merchantName = merchant.getMerchantName().replace(" ", "");
        var currentStringDate = DateTimeUtil.getCurrentLocalTime("yyyy-MM-dd HH:mm:ss");

        var plainApiSecret = apiKey + "-" + merchantName + "-" + currentStringDate;
        var encryptedApiSecret = encryptString(plainApiSecret, encryptionPassword);

        return ApiKeyAndSecretDto.builder()
                .apiKey(apiKey)
                .secret(encryptedApiSecret)
                .build();
    }

    private static String encryptString(String plainText, String encryptionPassword){
        var textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(encryptionPassword);
        return textEncryptor.encrypt(plainText);
    }

    public static String decryptString(String encryptedText, String encryptionPassword){
        var textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(encryptionPassword);
        return textEncryptor.decrypt(encryptedText);
    }
}
