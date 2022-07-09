package net.serg.currencyratetelegrambot.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CurrencyRateClientConfig.class)
public class ApplicationConfig {

}
