package net.serg.telegrambotstatistics.service;

public class BotException extends RuntimeException {
    public BotException(String message, Throwable cause) {
        super(message, cause);
    }
}
