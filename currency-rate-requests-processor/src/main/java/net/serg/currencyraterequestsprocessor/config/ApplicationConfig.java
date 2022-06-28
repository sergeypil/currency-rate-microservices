package net.serg.currencyraterequestsprocessor.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(EcbRateClientConfig.class)
public class ApplicationConfig {

}
