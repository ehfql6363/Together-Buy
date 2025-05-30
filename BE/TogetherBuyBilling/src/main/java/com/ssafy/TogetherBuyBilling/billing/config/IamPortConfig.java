package com.ssafy.TogetherBuyBilling.billing.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class IamPortConfig {

//    @Value("${imp.api.key}")
    private String apiKey;

//    @Value("${imp.api.secretkey}")
    private String secretKey;

//    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, secretKey);
    }
}
