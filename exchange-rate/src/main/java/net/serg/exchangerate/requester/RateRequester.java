package net.serg.exchangerate.requester;

public interface RateRequester {

    public String getRatesAsJson(final String url);
}
