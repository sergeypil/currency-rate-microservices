package net.serg.ecbhistoricalcurrencyrate.parser;

import net.serg.ecbhistoricalcurrencyrate.model.CurrencyRate;

import java.util.List;

public interface CurrencyRateParser {

    List<CurrencyRate> parse(String ratesAsString);
}
