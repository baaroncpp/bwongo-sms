package com.bwongo.sms_integration_service.base.config;

import com.bwongo.sms_integration_service.security.network.MerchantSecurityClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * @Author bkaaron
 * @Project bwongo-sms
 * @Date 10/29/24
 * @Time 10:40 PM
 **/
@Configuration
public class WebClientConfig {

    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    public WebClient merchantSecurityWebClient(){
        return WebClient.builder()
                .baseUrl("http://core-service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public MerchantSecurityClient merchantSecurityClient(){
        var httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(merchantSecurityWebClient()))
                .build();

        return httpServiceProxyFactory.createClient(MerchantSecurityClient.class);
    }

}
