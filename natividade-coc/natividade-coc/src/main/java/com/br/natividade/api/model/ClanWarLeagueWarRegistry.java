package com.br.natividade.api.model;

import java.util.List;

public record ClanWarLeagueWarRegistry(
        String state,
        int teamSize,
        String  preparationStartTime,
        String startTime,
        String endTime,
        ClanWarLeagueWarClan clan,
        ClanWarLeagueWarClan opponent,
        String warStartTime
) {
}
