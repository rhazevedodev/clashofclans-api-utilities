package com.br.natividade.api.model;

public record Location(
        int id, String name, boolean isCountry, String countryCode
) {
}
