package net.serg.currencyratetelegrambot.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.serg.currencyratetelegrambot.config.CurrencyRateClientConfig;
import net.serg.currencyratetelegrambot.model.CurrencyRate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyRateClientImpl implements CurrencyRateClient {
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final CurrencyRateClientConfig config;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Override
    public CurrencyRate getCurrencyRate(String rateType, String currency, LocalDate date) {
        log.info("getCurrencyRate rateType:{}, currency:{}, date:{}", rateType, currency, date);
        var urlWithParams = String.format("%s/%s/%s/%s", config.getUrl(), rateType, currency, DATE_FORMATTER.format(date));

        try {
            var response = httpClient.performRequest(urlWithParams);
            return objectMapper.readValue(response, CurrencyRate.class);
        } catch (HttpClientException ex) {
            log.error("Error while making request to URL: " + urlWithParams + " " + ex.getMessage());
            throw new CurrencyRateClientException("Error while making request to URL: " + urlWithParams + " " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Getting currencyRate error, currency:{}, date:{}", currency, date, ex);
            throw new CurrencyRateClientException("Can't get currencyRate. currency:" + currency + ", date:" + date);
        }
    }


}
