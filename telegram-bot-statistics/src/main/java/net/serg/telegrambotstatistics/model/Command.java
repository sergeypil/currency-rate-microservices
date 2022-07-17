package net.serg.telegrambotstatistics.model;

public enum Command {
    ANY_DATE_INFO("/any"),
    CURRENCY_CHAR_CODES("/currencycodes"),
    USD("/usd"),
    EUR("/eur"),
    RUB("/rub");

    private final String text;

    Command(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

