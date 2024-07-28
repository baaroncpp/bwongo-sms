package com.bwongo.core.base.model.enums;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 5/7/24
 * @LocalTime 2:56 PM
 **/
public enum CollateralTypeEnum {
    REAL_ESTATE("includes residential or commercial properties"),
    VEHICLE("vehicles such as cars, trucks, motorcycles, or boats"),
    EQUIPMENT("include manufacturing equipment, construction machinery, or any other valuable equipment used in business operations"),
    INVENTORY("of retail or manufacturing businesses"),
    ACCOUNTS_RECEIVABLE("unpaid invoices or future income"),
    INVESTMENTS("such as stocks, bonds, or mutual funds"),
    CASH(" include savings accounts, certificates of deposit (CDs), or money market accounts"),
    PERSONAL_PROPERTY("includes valuable personal assets such as jewelry, art, collectibles, or valuable household items"),
    FUTURE_INCOME("borrower's future income"),
    GUARANTEES_OR_COSIGNERS("a guarantor or cosigner pledges to repay the loan if the borrower defaults"),
    OTHERS("others");


    final String description;

    CollateralTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
