package net.serg.currencyratetelegrambot.clients;

public class HttpClientException extends RuntimeException {
    public HttpClientException(String msg) {
        super(msg);
    }
}
