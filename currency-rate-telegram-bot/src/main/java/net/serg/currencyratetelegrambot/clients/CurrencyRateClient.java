package net.serg.currencyratetelegrambot.clients;

import net.serg.currencyratetelegrambot.model.CurrencyRate;
import java.time.LocalDate;

public interface CurrencyRateClient {

    CurrencyRate getCurrencyRate(String rateType, String currency, LocalDate date,
                                 ClientExceptions clientExceptions);
}
