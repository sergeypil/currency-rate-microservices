package net.serg.ecbhistoricalcurrencyrate.controller;

import net.serg.ecbhistoricalcurrencyrate.config.ApplicationConfig;
import net.serg.ecbhistoricalcurrencyrate.config.JsonConfig;
import net.serg.ecbhistoricalcurrencyrate.model.CurrencyRate;
import net.serg.ecbhistoricalcurrencyrate.parser.CurrencyRateParserJson;
import net.serg.ecbhistoricalcurrencyrate.service.CurrencyRateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(CurrencyRateController.class)
@Import({ApplicationConfig.class, JsonConfig.class, CurrencyRateService.class, CurrencyRateParserJson.class})
class CurrencyRateControllerTest {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CurrencyRateService currencyRateService;

    @Test
    @DirtiesContext
    void getCurrencyRateTest() throws Exception {
        //given
        var currency = "USD";
        var date = "15-03-2020";
        var dateParam = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        var currencyRate = new CurrencyRate(currency, "419.639");

        //when
        when(currencyRateService.getCurrencyRate(currency, dateParam)).thenReturn(currencyRate);
        var result = mockMvc.perform(get(String.format("/api/v1/currencyRate/%s/%s", currency, date)))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        //then
        assertThat(result).isEqualTo("{\"charCode\":\"USD\",\"value\":\"419.639\"}");
    }


}