package net.serg.telegrambotstatistics.service;

import net.serg.telegrambotstatistics.model.Statistics;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface TelegramRequestStatisticsProcessor {
    void processMessage(Message message);
    Statistics getStatistics();
}
