package com.iamatum.beerclient.domain;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;


import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets BeerStyleEnum
 */
public enum BeerStyleEnum {
    LAGER("LAGER"),
    PILSNER("PILSNER"),
    STOUT("STOUT"),
    GOSE("GOSE"),
    PORTER("PORTER"),
    ALE("ALE"),
    WHEAT("WHEAT"),
    IPA("IPA"),
    PALE_ALE("PALE_ALE"),
    SAISON("SAISON");

    private String value;

    BeerStyleEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static BeerStyleEnum fromValue(String text) {
        for (BeerStyleEnum b : BeerStyleEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}

