package com.br.natividade.api.model;

import java.util.List;

public record ClanWarMember(
        String tag,
        String name,
        int townhallLevel,
        int mapPosition,
        int opponentAttacks,
        ClanWarLeagueWarBestOpponentAttack bestOpponentAttack,
        List<ClanWarLeagueWarAttacks> attacks
) {
}
