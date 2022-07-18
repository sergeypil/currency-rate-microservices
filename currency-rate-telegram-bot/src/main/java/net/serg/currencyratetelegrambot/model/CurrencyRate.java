package net.serg.currencyratetelegrambot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CurrencyRate {
    String charCode;
    String value;

    @JsonCreator
    public CurrencyRate(@JsonProperty("charCode") String charCode,
                        @JsonProperty("value") String value) {
        this.charCode = charCode;
        this.value = value;
    }
}
