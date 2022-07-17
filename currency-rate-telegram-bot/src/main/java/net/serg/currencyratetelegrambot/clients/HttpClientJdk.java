package net.serg.currencyratetelegrambot.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class HttpClientJdk implements HttpClient {
    private static final String HTTP_REQUEST_URL = "http request, url:{}";
    private static final String ERROR_WHILE_HTTP_REQUEST = "Error while making http request, url:{}";

    @Override
    public String performRequest(String url) {
        log.info(HTTP_REQUEST_URL, url);
        var client = java.net.http.HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            if (ex instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            log.error(ERROR_WHILE_HTTP_REQUEST, url, ex);
            throw new HttpClientException(ex.getMessage());
        }
    }
}
