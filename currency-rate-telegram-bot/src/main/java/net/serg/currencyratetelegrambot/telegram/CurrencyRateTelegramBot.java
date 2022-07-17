package net.serg.currencyratetelegrambot.telegram;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.serg.currencyratetelegrambot.service.MessageSender;
import net.serg.currencyratetelegrambot.service.MessageTextProcessorImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class CurrencyRateTelegramBot extends TelegramLongPollingBot {

    private final SendMessage response = new SendMessage();

    private final String botUsername;
    private final String botToken;

    private final MessageTextProcessorImpl messageTextProcessorImpl;
    private final MessageSender messageSender;

    private final static String  RECEIVED_MESSAGE = "Received message: {}";

    public CurrencyRateTelegramBot(MessageTextProcessorImpl messageTextProcessorImpl,
                                   MessageSender messageSender,
                                   @Value("${telegram.bot.name}") String botUsername,
                                   @Value("${telegram.bot.token}") String botToken) throws TelegramApiException {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.messageTextProcessorImpl = messageTextProcessorImpl;
        this.messageSender = messageSender;
    }
    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        log.info(RECEIVED_MESSAGE, update);
        if(update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            messageSender.send(message);
            String replyText = messageTextProcessorImpl.process(message.getText());
            response.setText(replyText);
            response.setChatId(update.getMessage().getChatId().toString());
            response.setReplyToMessageId(message.getMessageId());
            execute(response);
        }
    }
}
