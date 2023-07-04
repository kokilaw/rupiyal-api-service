package io.kokilaw.rupiyal;

import io.kokilaw.rupiyal.config.CurrencyRatesApiConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by kokilaw on 2023-07-04
 */
@Configuration
public class ApplicationConfiguration {

    @Bean
    @Qualifier(value = "currencyRatesAPIHttpClient")
    public RestTemplate currencyRatesAPIHttpClient(CurrencyRatesApiConfig currencyRatesApiConfig) {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(currencyRatesApiConfig.getConnectTimeOut());
        factory.setReadTimeout(currencyRatesApiConfig.getReadTimeOut());
        return new RestTemplate(factory);
    }

}
