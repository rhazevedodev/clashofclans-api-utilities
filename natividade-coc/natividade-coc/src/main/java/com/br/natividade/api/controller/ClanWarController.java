package com.br.natividade.api.controller;

import com.br.natividade.api.model.ClanWarAttacksSummary;
import com.br.natividade.api.service.ClanWarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/clanWar")
public class ClanWarController {

    private final ClanWarService clanWarService;

    public ClanWarController(ClanWarService clanWarService) {
        this.clanWarService = clanWarService;
    }

    @GetMapping("/currentDataWarAttacks")
    public ClanWarAttacksSummary getCurrentDataWarAttacks(@RequestParam String tag) {
        try {
            return clanWarService.getClanWarAttacksSummary(tag);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao processar a requisição", e);
        }
    }
}
