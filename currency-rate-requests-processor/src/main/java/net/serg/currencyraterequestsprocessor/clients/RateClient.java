package net.serg.currencyraterequestsprocessor.clients;

import net.serg.currencyraterequestsprocessor.model.CurrencyRate;

import java.time.LocalDate;

public interface RateClient {

    CurrencyRate getCurrencyRate(String currency, LocalDate date);
}
