package com.bwongo.core.base.utils;

import com.bwongo.core.base.model.enums.*;

import java.util.Arrays;
import java.util.List;

import static com.bwongo.core.base.model.enums.CollateralTypeEnum.*;
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

    public static boolean isChargeTypeEnum(String value){
        List<String> chargeTypeEnumList = Arrays.asList(
                ChargeTypeEnum.SMS.name(),
                ChargeTypeEnum.ACTIVATION.name(),
                ChargeTypeEnum.ANNUAL.name(),
                ChargeTypeEnum.MONTHLY.name(),
                ChargeTypeEnum.WEEKLY.name(),
                ChargeTypeEnum.WITHDRAWAL.name(),
                ChargeTypeEnum.LOAN_DISBURSEMENT.name(),
                ChargeTypeEnum.LOAN_INSTALLMENT.name(),
                ChargeTypeEnum.LOAN_OVERDUE.name()
        );
        return chargeTypeEnumList.contains(value);
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

    public static boolean isRepayPeriodEnum(String value){
        List<String> repayPeriodEnumList = Arrays.asList(
                RepayPeriodEnum.DAY.name(),
                RepayPeriodEnum.WEEK.name(),
                RepayPeriodEnum.MONTH.name()
        );
        return repayPeriodEnumList.contains(value);
    }

    public static boolean isAmortizationEnum(String value){
        List<String> amortizationEnumList = Arrays.asList(
                AmortizationEnum.EQUAL_INSTALLMENTS.name(),
                AmortizationEnum.EQUAL_PRINCIPAL_PAYMENTS.name()
        );
        return amortizationEnumList.contains(value);
    }

    public static boolean isInterestMethodEnum(String value){
        List<String> interestMethodEnumList = Arrays.asList(
                InterestMethodEnum.FLAT.name(),
                InterestMethodEnum.DECLINING_BALANCE.name()
        );
        return interestMethodEnumList.contains(value);
    }

    public static boolean isInterestCalculationPeriodEnum(String value){
        List<String> interestCalculationPeriodList = Arrays.asList(
                InterestCalculationPeriodEnum.SAME_AS_REPAYMENT_PERIOD.name(),
                InterestCalculationPeriodEnum.DAILY.name()
        );
        return interestCalculationPeriodList.contains(value);
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

    public static boolean isCollateralTypeEnum(String value){
        List<String> collateralTypeEnumList = Arrays.asList(
                REAL_ESTATE.name(),
                VEHICLE.name(),
                EQUIPMENT.name(),
                INVENTORY.name(),
                ACCOUNTS_RECEIVABLE.name(),
                INVESTMENTS.name(),
                CASH.name(),
                PERSONAL_PROPERTY.name(),
                FUTURE_INCOME.name(),
                GUARANTEES_OR_COSIGNERS.name(),
                OTHERS.name()
        );
        return collateralTypeEnumList.contains(value);
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

    public static boolean isInterestCompoundingPeriodEnum(String value){
        var interestCompoundingPeriodEnumList = Arrays.asList(
                InterestCompoundingPeriodEnum.ANNUALLY.name(),
                InterestCompoundingPeriodEnum.DAILY.name(),
                InterestCompoundingPeriodEnum.SEMI_ANNUALLY.name(),
                InterestCompoundingPeriodEnum.QUARTERLY.name(),
                InterestCompoundingPeriodEnum.MONTHLY.name()
        );
        return interestCompoundingPeriodEnumList.contains(value);
    }

    public static boolean isInterestPostingPeriodEnum(String value){
        var interestPostingPeriodEnumList = Arrays.asList(
                InterestPostingPeriodEnum.ANNUALLY.name(),
                InterestPostingPeriodEnum.DAILY.name(),
                InterestPostingPeriodEnum.SEMI_ANNUALLY.name(),
                InterestPostingPeriodEnum.QUARTERLY.name(),
                InterestPostingPeriodEnum.MONTHLY.name()
        );
        return interestPostingPeriodEnumList.contains(value);
    }

    public static boolean isInterestCalculatedUsingEnum(String value){
        var interestCalculatedUsingEnumList = Arrays.asList(
                InterestCalculatedUsingEnum.AVERAGE_BALANCE.name(),
                InterestCalculatedUsingEnum.DAILY_BALANCE.name()
        );
        return interestCalculatedUsingEnumList.contains(value);
    }

    public static boolean isDaysInYearEnum(String value){
        var daysInYearEnumList = Arrays.asList(
                DaysInYearEnum.DAYS_360.name(),
                DaysInYearEnum.DAYS_365.name()
        );
        return daysInYearEnumList.contains(value);
    }

    public static boolean isLockInPeriodEnum(String value){
        var lockInPeriodEnumList = Arrays.asList(
                LockInPeriodEnum.DAYS.name(),
                LockInPeriodEnum.WEEKS.name(),
                LockInPeriodEnum.MONTHS.name(),
                LockInPeriodEnum.YEARS.name()
        );
        return lockInPeriodEnumList.contains(value);
    }

    public static boolean isChargeCalculationTypeEnum(String value){
        var chargeCalculationTypeEnumList = Arrays.asList(
                ChargeCalculationTypeEnum.FLAT.name(),
                ChargeCalculationTypeEnum.PERCENTAGE.name()
        );
        return chargeCalculationTypeEnumList.contains(value);
    }
}
