package com.bwongo.core.account_mgt.utils;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;
import com.bwongo.core.account_mgt.models.jpa.TAccount;
import com.bwongo.core.base.model.enums.AccountStatusEnum;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

import static com.bwongo.core.merchant_mgt.utils.MerchantMsgConstants.*;
import static com.bwongo.core.account_mgt.utils.AccountMsgUtils.*;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 9/4/24
 * @LocalTime 12:18 AM
 **/
public class AccountUtils {

    private AccountUtils() {}

    public static BiConsumer<TAccount, BigDecimal> checkIfAccountIsValid = (account, smsCost) -> {
        Validate.isTrue(account.getStatus().equals(AccountStatusEnum.ACTIVE), ExceptionType.BAD_REQUEST, ACCOUNT_ERROR, account.getStatus().name());
        Validate.isTrue(account.getMerchant().isActive(), ExceptionType.BAD_REQUEST, MERCHANT_IS_INACTIVE);
        Validate.isTrue(!account.isDeleted(), ExceptionType.BAD_REQUEST, ACCOUNT_DELETED);
        Validate.isTrue(account.getCurrentBalance().compareTo(smsCost) > 0, ExceptionType.BAD_REQUEST, INSUFFICIENT_FUNDS);
    };
}
