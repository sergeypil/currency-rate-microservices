package net.serg.currencyratetelegrambot.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    private static final String ERROR_WHILE_HTTP_REQUEST = "Error while making http request, url: ";
    private static final String ERROR_PARSING_JSON = "Error parsing JSON: ";
    private static final String GETTING_CURRENCY_RATE = "Getting CurrencyRate rateType:{}, currency:{}, date:{}";

    private final CurrencyRateClientConfig config;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Override
    public CurrencyRate getCurrencyRate(String rateType, String currency, LocalDate date,
                                        ClientExceptions clientExceptions) {
        log.info(GETTING_CURRENCY_RATE, rateType, currency, date);
        var urlWithParams = String.format("%s/%s/%s/%s", config.getUrl(), rateType, currency, DATE_FORMATTER.format(date));

        String response = "";
        try {
            response = httpClient.performRequest(urlWithParams);
            return objectMapper.readValue(response, CurrencyRate.class);
        } catch (HttpClientException e) {
            clientExceptions.getExceptions().put(ERROR_WHILE_HTTP_REQUEST + urlWithParams, e);
        } catch (JsonProcessingException e) {
            clientExceptions.getExceptions().put(ERROR_PARSING_JSON + response, e);
        }
        return new CurrencyRate();
    }


}
