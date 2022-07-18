package net.serg.ecbhistoricalcurrencyrate.service;

import net.serg.ecbhistoricalcurrencyrate.config.ApplicationConfig;
import net.serg.ecbhistoricalcurrencyrate.config.JsonConfig;
import net.serg.ecbhistoricalcurrencyrate.model.CurrencyRate;
import net.serg.ecbhistoricalcurrencyrate.parser.CurrencyRateParserJson;
import net.serg.ecbhistoricalcurrencyrate.requester.RateRequester;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Import({ApplicationConfig.class, JsonConfig.class, CurrencyRateService.class, CurrencyRateParserJson.class})
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "app.cache.size=365")
class CurrencyRateServiceTest {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    @MockBean
    RateRequester rateRequester;

    @Autowired
    CurrencyRateService currencyRateService;

    @Test
    @DirtiesContext
    void getCurrencyRateTest() throws Exception {
        //given
        var currency = "USD";
        var date = "15-03-2020";
        var dateParam = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        //when
        when(rateRequester.getRatesAsJson(any())).thenReturn(TestData.ecbMockResponse);
        CurrencyRate currencyRate = currencyRateService.getCurrencyRate(currency, dateParam);


        assertThat(currencyRate.getCharCode()).isEqualTo("USD");
        assertThat(currencyRate.getValue()).isEqualTo("419.639");
        verify(rateRequester,times(1)).getRatesAsJson(any());
    }

    @Test
    @DirtiesContext
    void cacheUseTest() throws Exception {
        //given
        var currency = "EUR";
        var date = "15-03-2020";
        var dateParam = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        //when
        when(rateRequester.getRatesAsJson(any())).thenReturn(TestData.ecbMockResponse);

        currencyRateService.getCurrencyRate(currency, dateParam);
        currencyRateService.getCurrencyRate(currency, dateParam);

        currency = "USD";
        currencyRateService.getCurrencyRate(currency, dateParam);

        date = "16-03-2020";
        dateParam = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        currencyRateService.getCurrencyRate(currency, dateParam);

        //then
        verify(rateRequester, times(2)).getRatesAsJson(any());
    }
}