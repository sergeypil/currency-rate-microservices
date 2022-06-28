package net.serg.ecbhistoricalcurrencyrate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.serg.ecbhistoricalcurrencyrate.model.CurrencyRate;
import net.serg.ecbhistoricalcurrencyrate.service.CurrencyRateService;
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
public class CurrencyRateController {

    public final CurrencyRateService currencyRateService;

    @GetMapping("/currencyRate/{currency}/{date}")
    public CurrencyRate getCurrencyRate(@PathVariable("currency") String currency,
                                        @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("date") LocalDate date) throws Exception {
        log.info("GetCurrencyRate, currency:{}, date:{}", currency, date);
        var rate = currencyRateService.getCurrencyRate(currency, date);
        log.info("Rate:{}", rate);
        return rate;
    }
}
