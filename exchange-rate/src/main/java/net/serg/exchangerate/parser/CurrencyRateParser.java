package net.serg.exchangerate.parser;

import net.serg.exchangerate.model.CurrencyRate;

import java.util.List;

public interface CurrencyRateParser {

    List<CurrencyRate> parse(String ratesAsString);
}
