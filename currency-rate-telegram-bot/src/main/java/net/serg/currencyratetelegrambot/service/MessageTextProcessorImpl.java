package net.serg.currencyratetelegrambot.service;

import lombok.extern.slf4j.Slf4j;
import net.serg.currencyratetelegrambot.clients.CurrencyRateClient;
import net.serg.currencyratetelegrambot.clients.CurrencyRateClientException;
import net.serg.currencyratetelegrambot.model.CurrencyRate;
import net.serg.currencyratetelegrambot.model.Messages;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class MessageTextProcessorImpl implements MessageTextProcessor {
    private static final String ECB_SOURCE_OF_DATE = "ECB";
    private static final String DATE_FORMAT_ZERO = "dd-MM-yyyy";
    private static final String DATE_FORMAT = "d-MM-yyyy";
    private static final DateTimeFormatter DATE_FORMATTER_ZERO = DateTimeFormatter.ofPattern(DATE_FORMAT_ZERO);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final String TELEGRAM_COMMAND_START = "/start";
    private static final String TELEGRAM_COMMAND_CURRENCY_CODES = "/currencycodes";
    private static final String TELEGRAM_COMMAND_ANY = "/any";

    CurrencyRateClient currencyRateClient;

    public MessageTextProcessorImpl(CurrencyRateClient currencyRateClient) {
        this.currencyRateClient = currencyRateClient;
    }

    public String process(String messageText) {
        var textParts = messageText.split(" ");
        String currencyRateValue = "";
        if (textParts.length == 1) {
            currencyRateValue = processOneWordCommand(textParts);
        }
        else if (textParts.length == 2) {
            currencyRateValue = processTwoWordCommand(textParts);
           }
        else {
            currencyRateValue = Messages.ERROR.getText() + Messages.START.getText();
        }
        return currencyRateValue;
    }

    private String processOneWordCommand(String[] textParts) {
        String command = textParts[0];
        if (command.equalsIgnoreCase(TELEGRAM_COMMAND_START)) {
            return Messages.START.getText();
        }
        else if (command.equalsIgnoreCase(TELEGRAM_COMMAND_CURRENCY_CODES)) {
            return Messages.CURRENCY_CHAR_CODES.getText();
        }
        else if (command.equalsIgnoreCase(TELEGRAM_COMMAND_ANY)) {
            return Messages.ANY_DATE_INFO.getText();
        }
        else {
            String currencyCharCode = command.startsWith("/") ? command.substring(1): command;
            return performRequest(currencyCharCode, LocalDate.ofInstant(Instant.now(), ZoneOffset.UTC));
        }
    }

    private String processTwoWordCommand(String[] textParts) {
        String currencyCharCode = textParts[0];
        String dateAsString = textParts[1];
        LocalDate date = parseDate(dateAsString);
        return performRequest(currencyCharCode, date);
    }

    private LocalDate parseDate(String dateAsString) {
        try {
            return LocalDate.parse(dateAsString, DATE_FORMATTER_ZERO);
        } catch (Exception ex) {
            return LocalDate.parse(dateAsString, DATE_FORMATTER);
        }
    }

    private String performRequest(String currencyCharCode, LocalDate date) {
        try {
            CurrencyRate currencyRate = currencyRateClient.getCurrencyRate(ECB_SOURCE_OF_DATE, currencyCharCode, date);
            if(currencyRate.getValue() == null || currencyRate.getValue().equals("")) {
                return Messages.ERROR.getText() + Messages.START.getText();
            }
            else return currencyRate.getValue();
        }
        catch (CurrencyRateClientException e) {
            return Messages.ERROR.getText() + Messages.START.getText();
        }
    }
}
