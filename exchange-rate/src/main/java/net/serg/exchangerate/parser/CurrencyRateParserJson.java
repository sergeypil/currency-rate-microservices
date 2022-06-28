package net.serg.exchangerate.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.serg.exchangerate.model.CurrencyRate;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CurrencyRateParserJson implements CurrencyRateParser {
    @Override
    public List<CurrencyRate> parse(String ratesAsString) {
        var rates = new ArrayList<CurrencyRate>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(ratesAsString);
        } catch (JsonProcessingException e) {
            log.error("Parsing error, json:{}", ratesAsString, e);
            throw new CurrencyRateParsingException(e);
        }
        JsonNode jsonNodeRates = root.get("rates");
        jsonNodeRates.fields().forEachRemaining(e -> {
            rates.add(new CurrencyRate(e.getKey(), e.getValue().toString()));
                });
        return rates;
    }
}
