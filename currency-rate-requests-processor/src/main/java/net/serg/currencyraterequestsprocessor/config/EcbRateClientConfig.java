package net.serg.currencyraterequestsprocessor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ecb-rate-client")
public class EcbRateClientConfig {
    String url;
}
