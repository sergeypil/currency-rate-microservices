package net.serg.ecbhistoricalcurrencyrate.model;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class CachedCurrencyRates implements Serializable {
    List<CurrencyRate> currencyRates;
}
