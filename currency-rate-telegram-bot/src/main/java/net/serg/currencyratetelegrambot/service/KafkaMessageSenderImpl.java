package net.serg.currencyratetelegrambot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageSenderImpl implements MessageSender {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String SEND_MESSAGE ="Send message:{}";
    private static final String CANT_SERIALIZE_MESSAGE = "Can't serialize message:{}";

    @Value("${topic.requests}")
    private String TOPIC_RATE_REQUESTS;

    @Override
    public void send(Message message) {
        log.info(SEND_MESSAGE, message);
        String messageAsString;
        try {
            messageAsString = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            log.error(CANT_SERIALIZE_MESSAGE, message, e);
            throw new RuntimeException(e);
        }
        kafkaTemplate.send(TOPIC_RATE_REQUESTS, messageAsString);
    }
}
