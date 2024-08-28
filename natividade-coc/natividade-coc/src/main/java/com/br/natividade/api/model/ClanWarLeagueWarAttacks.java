package com.br.natividade.api.model;

public record ClanWarLeagueWarAttacks(
        String attackerTag,
        String defenderTag,
        int stars,
        int destructionPercentage,
        int order,
        int duration
) {
}
