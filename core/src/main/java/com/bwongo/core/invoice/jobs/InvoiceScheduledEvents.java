package com.bwongo.core.invoice.jobs;

import com.bwongo.core.invoice.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 9/29/24
 * @Time 10:27 AM
 **/
@Service
@Slf4j
@RequiredArgsConstructor
@Configuration
public class InvoiceScheduledEvents {

    private final InvoiceService invoiceService;

    @Scheduled(fixedDelay = 60000, initialDelay = 30000)
    public void sendMonthlyInvoice(){
        try {
            log.info("Sending monthly invoices");
            invoiceService.sendMonthlyInvoice();
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
