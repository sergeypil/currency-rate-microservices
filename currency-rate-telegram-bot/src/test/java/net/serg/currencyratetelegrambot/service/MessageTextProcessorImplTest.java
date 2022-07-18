package net.serg.currencyratetelegrambot.service;

import net.serg.currencyratetelegrambot.clients.ClientExceptions;
import net.serg.currencyratetelegrambot.clients.CurrencyRateClient;
import net.serg.currencyratetelegrambot.model.CurrencyRate;
import net.serg.currencyratetelegrambot.model.Message;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MessageTextProcessorImplTest {
    @Test
    void processTestOneArg() {
        //given
        var currencyRateClient = mock(CurrencyRateClient.class);
        var clientExceptions = mock(ClientExceptions.class);
        var currencyRate = new CurrencyRate("USD", "434");
        when(currencyRateClient.getCurrencyRate("ECB", "USD",
                LocalDate.now(), clientExceptions))
                .thenReturn(currencyRate);

        var messageTextProcessor= new MessageTextProcessorImpl(currencyRateClient, clientExceptions);
        var msg = "/USD";

        //when
        var result = messageTextProcessor.process(msg);

        //then
        assertThat(result).isEqualTo(currencyRate.getValue());
    }

    @Test
    void processTestWrongArg() {
        //given
        String errorWhileHttpRequest = "Error while making http request, url: ";
        String wrongCharCode = "WRONG_CHAR_CODE";
        var currencyRateClient = mock(CurrencyRateClient.class);
        var clientExceptions = mock(ClientExceptions.class);
        var currencyRate = new CurrencyRate("USD", "434");
        when(currencyRateClient.getCurrencyRate("ECB", wrongCharCode,
                LocalDate.now(), clientExceptions))
                .thenReturn(new CurrencyRate());
        when(clientExceptions.getExceptions()).thenReturn(Map.of());

        var messageTextProcessor= new MessageTextProcessorImpl(currencyRateClient, clientExceptions);
        //when
        var result = messageTextProcessor.process(wrongCharCode);

        //then
        assertThat(result).isEqualTo(Message.ERROR.getText() + Message.START.getText());
    }
}