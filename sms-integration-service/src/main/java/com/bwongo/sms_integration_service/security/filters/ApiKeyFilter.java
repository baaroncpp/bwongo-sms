package com.bwongo.sms_integration_service.security.filters;

import com.bwongo.sms_integration_service.security.network.MerchantSecurityClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/29/24
 * @Time 11:30 PM
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class ApiKeyFilter  extends OncePerRequestFilter {

    private final MerchantSecurityClient merchantSecurityClient;

    private static final String API_KEY_HEADER = "x-api-key";
    private static final String MERCHANT_CODE = "code";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var apiKey = request.getHeader(API_KEY_HEADER);
        var merchantCode = request.getHeader(MERCHANT_CODE);

        if (merchantSecurityClient.validateMerchantKeyValue(apiKey, merchantCode)) {
            filterChain.doFilter(request, response);
        } else {
            log.error("unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
