package net.serg.ecbhistoricalcurrencyrate.requester;

public interface RateRequester {

    public String getRatesAsJson(final String url);
}
