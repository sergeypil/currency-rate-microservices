package net.serg.currencyraterequestsprocessor.controller;

import net.serg.currencyraterequestsprocessor.model.CurrencyRate;
import net.serg.currencyraterequestsprocessor.model.RateType;
import net.serg.currencyraterequestsprocessor.service.CurrencyRateEndpointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "${app.rest.api.prefix}/v1")
public class CurrencyRateEndpointController {

    public final CurrencyRateEndpointService currencyRateEndpointService;

    @GetMapping("/currencyRate/{type}/{currency}/{date}")
    public CurrencyRate getCurrencyRate(@PathVariable("type") RateType type,
                                        @PathVariable("currency") String currency,
                                        @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("date") LocalDate date)  {
        log.info("GetCurrencyRate, currency:{}, date:{}", currency, date);

        var rate = currencyRateEndpointService.getCurrencyRate(type, currency, date);
        log.info("Rate:{}", rate);
        return rate;
    }
}