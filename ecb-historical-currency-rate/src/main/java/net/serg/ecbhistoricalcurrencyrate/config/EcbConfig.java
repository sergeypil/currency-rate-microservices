package net.serg.ecbhistoricalcurrencyrate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ecb")
public class EcbConfig {
    String url;
}