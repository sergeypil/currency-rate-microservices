package net.serg.currencyratetelegrambot.model;

public enum Message {
    ERROR("Мы не смогли обработать ваш запрос, попробуйте еще раз позже.\n"),
    START(
            "- Узнать курс доллара на сегодня /usd\n" +
                    "- Узнать курс евро на сегодня /eur\n" +
                    "- Узнать курс рубля на сегодня /rub\n" +
                    "- Узнать курс любой валюты на определенную дату в прошлом /any\n"
            ),
    ANY_DATE_INFO(
      "- Чтобы узнать курс валюты на определенную дату в прошлом, введите код валюты и дату через пробел(пример: EUR 25-05-2022)\n" +
              "- Коды валют /currencycodes"
    ),
    CURRENCY_CHAR_CODES(
            " Коды валют:\n" +
                    "USD - Американский доллар\n" +
                    "EUR - Евро\n" +
                    "RUB - Российский рубль\n" +
                    "CHY - Юань"
    );
    private final String text;

    Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
