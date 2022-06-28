package net.serg.exchangerate.model;

import lombok.Value;

@Value
public class CurrencyRate {
    String charCode;
    String value;
}
