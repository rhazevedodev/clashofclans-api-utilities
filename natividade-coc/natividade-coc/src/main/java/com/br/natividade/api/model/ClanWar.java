package com.br.natividade.api.model;

import java.time.OffsetDateTime;

public record ClanWar(
        String state,
        int teamSize,
        int attacksPerMember,
        String battleModifier,
        String preparationStartTime,
        String startTime,
        String endTime,
        WarClan clan,
        WarClan opponent
) {
}
