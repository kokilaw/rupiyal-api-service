package io.kokilaw.rupiyal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kokilaw on 2023-07-04
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "exchange-rates-api")
public class ExchangeRatesApiConfig {

    private String apiUrl;
    private int readTimeOut;
    private int connectTimeOut;

}
