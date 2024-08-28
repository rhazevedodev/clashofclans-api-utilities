package com.br.natividade.api.model;

import java.util.List;

public record ClanWarLeagueGroup(
        String state,
        String season,
        List<ClanWarLeagueClan> clans,
        List<ClanWarLeagueRound> rounds
) {
}
