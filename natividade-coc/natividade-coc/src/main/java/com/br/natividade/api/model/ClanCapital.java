package com.br.natividade.api.model;

import java.util.List;

public record ClanCapital(
        int capitalHallLevel,
        List<District> districts
) {
}
