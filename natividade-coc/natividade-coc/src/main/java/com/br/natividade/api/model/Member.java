package com.br.natividade.api.model;

public record Member(
        String tag,
        String name,
        String role,
        int townHallLevel,
        int expLevel,
        League league,
        int trophies,
        int builderBaseTrophies,
        int clanRank,
        int previousClanRank,
        int donations,
        int donationsReceived,
        PlayerHouse playerHouse,
        BuilderBaseLeague builderBaseLeague
) {
}
