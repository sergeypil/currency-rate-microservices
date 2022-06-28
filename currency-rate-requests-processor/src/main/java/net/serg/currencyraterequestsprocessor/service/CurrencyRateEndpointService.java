package net.serg.currencyraterequestsprocessor.service;

import net.serg.currencyraterequestsprocessor.clients.RateClient;
import net.serg.currencyraterequestsprocessor.model.CurrencyRate;
import net.serg.currencyraterequestsprocessor.model.RateType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
@Slf4j
public class CurrencyRateEndpointService {

    private final Map<String, RateClient> clients;

    public CurrencyRateEndpointService(Map<String, RateClient> clients) {
        this.clients = clients;
    }

    public CurrencyRate getCurrencyRate(RateType rateType, String currency, LocalDate date) {
        log.info("GetCurrencyRate. rateType:{}, currency:{}, date:{}", rateType, currency, date);
        var client = clients.get(rateType.getServiceName().toLowerCase());
        return client.getCurrencyRate(currency, date);
    }
}