package net.serg.exchangerate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.serg.exchangerate.config.EcbConfig;
import net.serg.exchangerate.model.CachedCurrencyRates;
import net.serg.exchangerate.model.CurrencyRate;
import net.serg.exchangerate.parser.CurrencyRateParser;
import net.serg.exchangerate.requester.RateRequester;
import org.ehcache.Cache;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyRateService {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final RateRequester rateRequester;
    private final CurrencyRateParser currencyRateParser;
    private final EcbConfig ecbConfig;
    private final Cache<LocalDate, CachedCurrencyRates> currencyRateCache;

    public CurrencyRate getCurrencyRate(String currency, LocalDate date) {
        List<CurrencyRate> rates;
        var cachedCurrencyRates = currencyRateCache.get(date);
        if (cachedCurrencyRates == null) {
            var urlWithParams = String.format("%s/%s?base=KZT", ecbConfig.getUrl(), DATE_FORMATTER.format(date));
            var ratesAsJson = rateRequester.getRatesAsJson(urlWithParams);
            rates = currencyRateParser.parse(ratesAsJson);
            currencyRateCache.put(date, new CachedCurrencyRates(rates));
        } else {
            rates = cachedCurrencyRates.getCurrencyRates();
        }
        return rates.stream().filter(rate -> currency.equalsIgnoreCase(rate.getCharCode()))
                .findFirst()
                .map(rate -> {
                    return new CurrencyRate(rate.getCharCode(),
                            (new BigDecimal(1).
                                    divide(new BigDecimal(rate.getValue()), 3, RoundingMode.HALF_UP)
                                    .toString()));
                })
                .orElseThrow(() -> new CurrencyRateNotFoundException("Currency Rate not found. Currency:" + currency + ", date:" + date));
    }
}