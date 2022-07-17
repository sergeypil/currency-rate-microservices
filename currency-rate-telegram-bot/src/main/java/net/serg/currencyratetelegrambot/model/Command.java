package net.serg.currencyratetelegrambot.model;

public enum Command {
    START("/start"),
    ANY_DATE_INFO("/any"),
    CURRENCY_CHAR_CODES("/currencycodes");

    private final String text;

    Command(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

