package com.bwongo.core.base.utils;

import com.bwongo.core.base.model.enums.*;

import java.util.Arrays;
import java.util.List;

import static com.bwongo.core.base.model.enums.PaymentPriorityOrderEnum.*;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/15/24
 * @LocalTime 8:38 PM
 **/
public class EnumValidation {

    private EnumValidation() {  }

    public static boolean isUserType(String value){
        List<String> userTypeEnumList = List.of(
                UserTypeEnum.ADMIN.name(),
                UserTypeEnum.SUPER_ADMIN.name(),
                UserTypeEnum.MERCHANT.name()
        );
        return userTypeEnumList.contains(value);

    }

    public static boolean isApprovalStatus(String value){
        List<String> approvalEnumList = Arrays.asList(
                ApprovalStatusEnum.APPROVED.name(),
                ApprovalStatusEnum.REJECTED.name(),
                ApprovalStatusEnum.PENDING.name()
        );
        return approvalEnumList.contains(value);
    }

    public static boolean isIdentificationType(String value){
        List<String> approvalEnumList = Arrays.asList(
                IdentificationTypeEnum.OTHER.name(),
                IdentificationTypeEnum.DRIVING_PERMIT.name(),
                IdentificationTypeEnum.PASSPORT.name(),
                IdentificationTypeEnum.NATIONAL_ID.name()
        );
        return approvalEnumList.contains(value);
    }

    public static boolean isCurrencyEnum(String value){
        List<String> currencyEnumList = Arrays.asList(
                CurrencyEnum.KSH.name(),
                CurrencyEnum.TZS.name(),
                CurrencyEnum.USD.name(),
                CurrencyEnum.UGX.name()
        );
        return currencyEnumList.contains(value);
    }

    public static boolean isMoratoriumEnum(String value){
        List<String> moratoriumList = Arrays.asList(
                MoratoriumEnum.INTEREST.name(),
                MoratoriumEnum.PRINCIPLE.name()
        );
        return moratoriumList.contains(value);
    }

    public static boolean isGenderEnum(String value){
        List<String> genderList = Arrays.asList(
                GenderEnum.MALE.name(),
                GenderEnum.FEMALE.name()
        );
        return genderList.contains(value);
    }

    public static boolean isMaritalStatusEnum(String value){
        List<String> maritalStatusList = Arrays.asList(
                MaritalStatusEnum.DIVORCED.name(),
                MaritalStatusEnum.MARRIED.name(),
                MaritalStatusEnum.SINGLE.name(),
                MaritalStatusEnum.SEPARATED.name(),
                MaritalStatusEnum.WIDOWED.name()
        );
        return maritalStatusList.contains(value);
    }

    public static boolean isPaymentPriorityOrderEnum(String value){
        List<String> paymentPriorityOrderEnumList = Arrays.asList(
                INTEREST_PRINCIPAL_PENALTY_CHARGE.name(),
                INTEREST_PRINCIPAL_CHARGE_PENALTY.name(),
                PRINCIPAL_INTEREST_PENALTY_CHARGE.name(),
                PRINCIPAL_INTEREST_CHARGE_PENALTY.name()
        );
        return paymentPriorityOrderEnumList.contains(value);
    }

    public static boolean isChargeCalculationTypeEnum(String value){
        var chargeCalculationTypeEnumList = Arrays.asList(
                ChargeCalculationTypeEnum.FLAT.name(),
                ChargeCalculationTypeEnum.PERCENTAGE.name()
        );
        return chargeCalculationTypeEnumList.contains(value);
    }

    public static boolean isMerchantTypeEnum(String value){
        var merchantTypeEnumList = Arrays.asList(
                MerchantTypeEnum.BUSINESS.name(),
                MerchantTypeEnum.COMPANY.name(),
                MerchantTypeEnum.PERSONAL.name(),
                MerchantTypeEnum.ORGANIZATION.name(),
                MerchantTypeEnum.OTHER.name()
        );
        return merchantTypeEnumList.contains(value);
    }

    public static boolean isPaymentTypeEnum(String value){
        var paymentTypeEnumList = Arrays.asList(
                PaymentTypeEnum.POSTPAID.name(),
                PaymentTypeEnum.PREPAID.name()
        );
        return paymentTypeEnumList.contains(value);
    }
}
