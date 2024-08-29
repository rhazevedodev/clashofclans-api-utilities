package com.br.natividade.api.model;

import java.util.List;
import java.util.Map;

public record WarClan(
        String tag,
        String name,
        Map<String, String> badgeUrls,
        int clanLevel,
        int attacks,
        int stars,
        double destructionPercentage,
        List<ClanWarMember> members
) {
}
