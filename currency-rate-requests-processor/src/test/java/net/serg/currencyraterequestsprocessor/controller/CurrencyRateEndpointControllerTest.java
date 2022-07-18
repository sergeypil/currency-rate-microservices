package net.serg.currencyraterequestsprocessor.controller;

import net.serg.currencyraterequestsprocessor.clients.EcbRateClient;
import net.serg.currencyraterequestsprocessor.clients.HttpClient;
import net.serg.currencyraterequestsprocessor.config.ApplicationConfig;
import net.serg.currencyraterequestsprocessor.config.EcbRateClientConfig;
import net.serg.currencyraterequestsprocessor.config.JsonConfig;
import net.serg.currencyraterequestsprocessor.service.CurrencyRateEndpointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyRateEndpointController.class)
@Import({ApplicationConfig.class, JsonConfig.class, CurrencyRateEndpointService.class, EcbRateClient.class})
@TestPropertySource(properties = "ecb-rate-client.url=http://localhost:8081/api/v1/currencyRate")
class CurrencyRateEndpointControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EcbRateClientConfig config;

    @MockBean
    HttpClient httpClient;

    @Test
    void getCurrencyRateTest() throws Exception {
        //given
        var type = "ECB";
        var currency = "USD";
        var date = "15-03-2020";

        var url = String.format("%s/%s/%s", config.getUrl(), currency, date);
        when(httpClient.performRequest(url))
                .thenReturn("{\"numCode\":\"978\",\"charCode\":\"EUR\",\"value\":\"419.639\"}");
        //when
        var result = mockMvc.perform(get(String.format("/api/v1/currencyRate/%s/%s/%s", type, currency, date)))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        //then
        assertThat(result).isEqualTo("{\"charCode\":\"EUR\",\"value\":\"419.639\"}");
    }
}