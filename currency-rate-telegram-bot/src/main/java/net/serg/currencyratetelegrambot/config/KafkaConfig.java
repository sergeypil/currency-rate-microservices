package net.serg.currencyratetelegrambot.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.config.TopicBuilder;


@Configuration
public class KafkaConfig {

    @Value("${topic.requests}")
    private String TOPIC_RATE_REQUESTS;

    @Bean
    public NewTopic topic() {
        return TopicBuilder
                .name(TOPIC_RATE_REQUESTS)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
