package net.serg.currencyratetelegrambot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.serg.currencyratetelegrambot.clients.ClientExceptions;
import net.serg.currencyratetelegrambot.clients.CurrencyRateClient;
import net.serg.currencyratetelegrambot.model.Command;
import net.serg.currencyratetelegrambot.model.CurrencyRate;
import net.serg.currencyratetelegrambot.model.Message;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageTextProcessorImpl implements MessageTextProcessor {
    private static final String ECB_SOURCE_OF_DATE = "ECB";
    private static final String DATE_FORMAT_ZERO = "dd-MM-yyyy";
    private static final String DATE_FORMAT = "d-MM-yyyy";
    private static final DateTimeFormatter DATE_FORMATTER_ZERO = DateTimeFormatter.ofPattern(DATE_FORMAT_ZERO);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final String COMMAND_DELIMITER = " ";

    private final CurrencyRateClient currencyRateClient;
    private final ClientExceptions clientExceptions;


    public String process(String messageText) {
        var textParts = messageText.split(COMMAND_DELIMITER);
        String currencyRateValue = "";
        if (textParts.length == 1) {
            return processOneWordCommand(textParts);
        } else if (textParts.length == 2) {
            currencyRateValue = processTwoWordCommand(textParts);
        } else {
            currencyRateValue = Message.ERROR.getText() + Message.START.getText();
        }
        return currencyRateValue;
    }

    private String processOneWordCommand(String[] textParts) {
        String command = textParts[0];
        if (command.equalsIgnoreCase(Command.START.getText())) {
            return Message.START.getText();
        } else if (command.equalsIgnoreCase(Command.CURRENCY_CHAR_CODES.getText())) {
            return Message.CURRENCY_CHAR_CODES.getText();
        } else if (command.equalsIgnoreCase(Command.ANY_DATE_INFO.getText())) {
            return Message.ANY_DATE_INFO.getText();
        } else {
            String currencyCharCode = command.startsWith("/") ? command.substring(1) : command;
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
        CurrencyRate currencyRate = currencyRateClient.getCurrencyRate(ECB_SOURCE_OF_DATE,
                currencyCharCode, date, clientExceptions);
        if (currencyRate.getValue() == null || currencyRate.getValue().equals("")) {
            return Message.ERROR.getText() + Message.START.getText();
        } else if (!clientExceptions.getExceptions().isEmpty()) {
            for (Map.Entry<String, Exception> entry : clientExceptions.getExceptions().entrySet()) {
                log.error(entry.getKey() + entry.getValue());
                return Message.ERROR.getText() + Message.START.getText();
            }
        }
        return currencyRate.getValue();
    }
}
