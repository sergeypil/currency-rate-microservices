package net.serg.telegrambotstatistics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Scope(SCOPE_PROTOTYPE)
public class Statistics {
    private AtomicLong requestCounter = new AtomicLong();
    private AtomicLong anyCounter = new AtomicLong();
    private AtomicLong charCodesCounter = new AtomicLong();
    private AtomicLong usdCounter = new AtomicLong();
    private AtomicLong eurCounter = new AtomicLong();
    private AtomicLong rubCounter = new AtomicLong();
}
