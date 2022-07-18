package net.serg.telegrambotstatistics.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.serg.telegrambotstatistics.service.BotException;
import net.serg.telegrambotstatistics.service.TelegramRequestStatisticsProcessor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;


@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableKafka
public class KafkaConfig {
    public static final String TOPIC_RATE_REQUESTS = "RATE_REQUESTS";
    public static final String GROUP_ID = "RateRequestsProcessor";
    public static final String CANT_PARSE_MESSAGE = "can't parse message: ";

    private final TelegramRequestStatisticsProcessor telegramRequestStatisticsProcessor;
    private final ObjectMapper objectMapper;

    @Bean
    public NewTopic topic() {
        return TopicBuilder
                .name(TOPIC_RATE_REQUESTS)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @KafkaListener(groupId = GROUP_ID, topics = TOPIC_RATE_REQUESTS)
    public void rateRequestListen(String msgAsString) {
        org.telegram.telegrambots.meta.api.objects.Message message;
        try {
            message = objectMapper.readValue(msgAsString,
                    org.telegram.telegrambots.meta.api.objects.Message.class);
        } catch (Exception e) {
            log.error(CANT_PARSE_MESSAGE + msgAsString, e);
            throw new BotException(CANT_PARSE_MESSAGE + msgAsString, e);
        }
        telegramRequestStatisticsProcessor.processMessage(message);
    }
}