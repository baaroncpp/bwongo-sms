package com.bwongo.core.invoice.service;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.DateTimeUtil;
import com.bwongo.commons.utils.Validate;
import com.bwongo.core.account_mgt.models.jpa.TAccount;
import com.bwongo.core.account_mgt.repository.TAccountRepository;
import com.bwongo.core.base.model.dto.response.PageResponseDto;
import com.bwongo.core.base.model.enums.AccountTypeEnum;
import com.bwongo.core.base.model.enums.InvoiceStatusEnum;
import com.bwongo.core.base.model.enums.PaymentStatusEnum;
import com.bwongo.core.base.model.enums.SmsStatusEnum;
import com.bwongo.core.base.service.AuditService;
import com.bwongo.core.invoice.models.dto.request.InvoiceRequestDto;
import com.bwongo.core.invoice.models.dto.response.InvoiceResponseDto;
import com.bwongo.core.invoice.models.jpa.TInvoice;
import com.bwongo.core.invoice.models.jpa.TInvoiceItem;
import com.bwongo.core.invoice.repository.TInvoiceItemRepository;
import com.bwongo.core.invoice.repository.TInvoiceRepository;
import com.bwongo.core.invoice.service.dto.InvoiceDtoService;
import com.bwongo.core.merchant_mgt.models.jpa.TMerchant;
import com.bwongo.core.merchant_mgt.repository.TMerchantRepository;
import com.bwongo.core.sms_mgt.models.jpa.TSms;
import com.bwongo.core.sms_mgt.repository.TSmsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.bwongo.core.account_mgt.utils.AccountMsgUtils.*;
import static com.bwongo.core.base.utils.BaseMsgUtils.ACCEPTABLE_DATE_FORMAT;
import static com.bwongo.core.base.utils.BaseUtils.pageToDto;
import static com.bwongo.core.invoice.utils.InvoiceMsgConstant.*;
import static com.bwongo.core.invoice.utils.InvoiceUtils.addDaysToDate;
import static com.bwongo.core.invoice.utils.InvoiceUtils.generateMerchantInvoiceNumber;
import static com.bwongo.core.merchant_mgt.utils.MerchantMsgConstants.MERCHANT_NOT_FOUND;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 9/22/24
 * @Time 12:33PM
 **/
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InvoiceService {

    private final TInvoiceRepository invoiceRepository;
    private final TInvoiceItemRepository invoiceItemRepository;
    private final TAccountRepository accountRepository;
    private final TSmsRepository smsRepository;
    private final AuditService auditService;
    private final InvoiceDtoService invoiceDtoService;
    private final TMerchantRepository merchantRepository;

    public InvoiceResponseDto sendMerchantInvoice(InvoiceRequestDto invoiceRequestDto){

        invoiceRequestDto.validate();
        var dueDate = getDateFromString(invoiceRequestDto.dueDate());
        var merchant = getMerchantById(invoiceRequestDto.merchantId());

        return createMerchantInvoice(merchant, dueDate);
    }

    public PageResponseDto getInvoices(Pageable pageable){

        var invoicePage = invoiceRepository.findAll(pageable);
        var invoiceList = invoicePage.stream()
                .map(invoiceDtoService::invoiceToDto)
                .toList();

        return pageToDto(invoicePage, invoiceList);
    }

    public void sendMonthlyInvoice(){

        var dueDate = addDaysToDate(new Date(), 7);
        var creditMerchantAccounts = accountRepository.findAllByAccountType(AccountTypeEnum.CREDIT);

        creditMerchantAccounts.forEach(account -> {
            var merchant = account.getMerchant();

            if(account.getCurrentBalance().compareTo(BigDecimal.ZERO) > 0){
                try {
                    createMerchantInvoice(merchant, dueDate);
                }catch (Exception e){
                    log.error("Error sending invoice for merchant : {}, message : {}", merchant.getMerchantName(), e.getMessage());
                }
            }
        });
    }

    public InvoiceResponseDto createMerchantInvoice(TMerchant merchant, Date dueDate){

        var merchantCreditAccount = getMerchantAccount(merchant, AccountTypeEnum.CREDIT);
        var creditAmount = merchantCreditAccount.getCurrentBalance();
        var merchantSms = getInvoiceItems(merchant);
        var invoiceItemsTotalCostAmount = merchantSms.stream()
                .map(TSms::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Validate.isTrue(creditAmount.compareTo(BigDecimal.ZERO) <= 0, ExceptionType.BAD_REQUEST, NO_CREDIT_TO_BE_INVOICED);
        Validate.isTrue(creditAmount.compareTo(invoiceItemsTotalCostAmount) == 0, ExceptionType.BAD_REQUEST, INVALID_INVOICE_UNEQUAL_AMOUNTS);

        var merchantInvoice = TInvoice.builder()
                .invoiceNumber(generateMerchantInvoiceNumber(merchant))
                .issuedOnDate(DateTimeUtil.getCurrentUTCTime())
                .totalAmount(creditAmount)
                .merchant(merchant)
                .invoiceStatus(InvoiceStatusEnum.ISSUED)
                .paymentDueDate(dueDate)
                .quantity((long)merchantSms.size())
                .build();

        auditService.stampLongEntity(merchant);
        var savedMerchantInvoice = invoiceRepository.save(merchantInvoice);

        persistInvoiceItems(merchantSms, savedMerchantInvoice);

        return invoiceDtoService.invoiceToDto(savedMerchantInvoice);
    }

    private void persistInvoiceItems(List<TSms> invoiceItems, TInvoice invoice){

        invoiceItems.forEach(item -> {
            var invoiceItem = TInvoiceItem.builder()
                    .sms(item)
                    .invoice(invoice)
                    .build();

            auditService.stampLongEntity(invoiceItem);
            invoiceItemRepository.save(invoiceItem);
        });
    }

    private List<TSms> getInvoiceItems(TMerchant merchant){

        var merchantSms = smsRepository.findAllByMerchantAndPaymentStatus(merchant, PaymentStatusEnum.NOT_PAID);
        var result =  new ArrayList<TSms>();

        merchantSms.forEach(item -> {
            if(!invoiceItemRepository.existsBySms(item) && item.getSmsStatus().equals(SmsStatusEnum.DELIVERED))
                result.add(item);
        });
        return result;
    }

    private List<TSms> getNonPaidDeliveredMonthSms(int month, int year, Long merchantId){

        var monthSms = smsRepository.findAllByMonth(month, year);
        return monthSms.stream()
                .filter(sms -> sms.getPaymentStatus().equals(PaymentStatusEnum.NOT_PAID) && Objects.equals(sms.getMerchant().getId(), merchantId))
                .toList();
    }

    private TAccount getMerchantAccount(TMerchant merchant, AccountTypeEnum accountType){
        var merchantAccount = accountRepository.findByMerchantAndAccountType(merchant, accountType);
        Validate.isPresent(merchantAccount, MERCHANT_ACCOUNT_NOT_FOUND, accountType.name(), merchant.getMerchantName());
        return merchantAccount.get();
    }

    private Date getDateFromString(String stringDate){
        Validate.isAcceptableDateFormat(stringDate);
        return DateTimeUtil.stringToDate(stringDate, ACCEPTABLE_DATE_FORMAT);
    }

    private TMerchant getMerchantById(Long id){
        var existingMerchant = merchantRepository.findById(id);
        Validate.isPresent(existingMerchant, MERCHANT_NOT_FOUND, id);
        return existingMerchant.get();
    }
}
