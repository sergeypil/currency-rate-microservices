package net.serg.telegrambotstatistics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.serg.telegrambotstatistics.model.Command;
import net.serg.telegrambotstatistics.model.Statistics;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;


@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramRequestStatisticsProcessorImpl implements TelegramRequestStatisticsProcessor {
    private static final String MESSAGE_DELIMITER = " ";

    private final Statistics statistics;

    @Override
    public void processMessage(Message message) {
        statistics.getRequestCounter().incrementAndGet();
        var command = message.getText().split(MESSAGE_DELIMITER)[0];

        if (command.equalsIgnoreCase(Command.EUR.getText())) {
            statistics.getEurCounter().incrementAndGet();
        } else if (command.equalsIgnoreCase(Command.USD.getText())) {
            statistics.getUsdCounter().incrementAndGet();
        } else if (command.equalsIgnoreCase(Command.RUB.getText())) {
            statistics.getRubCounter().incrementAndGet();
        } else if (command.equalsIgnoreCase(Command.ANY_DATE_INFO.getText())) {
            statistics.getAnyCounter().incrementAndGet();
        } else if (command.equalsIgnoreCase(Command.CURRENCY_CHAR_CODES.getText())) {
            statistics.getCharCodesCounter().incrementAndGet();
        }
    }

    @Override
    public Statistics getStatistics() {
        return statistics;
    }
}
