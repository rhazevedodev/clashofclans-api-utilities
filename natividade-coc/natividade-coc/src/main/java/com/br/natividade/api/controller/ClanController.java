package com.br.natividade.api.controller;

import com.br.natividade.api.service.ClanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/clan")
public class ClanController {

    private final ClanService clanService;

    public ClanController(ClanService clanService) {
        this.clanService = clanService;
    }

    @GetMapping("/endpoint")
    public String testEndpoint2(@RequestParam String tag) {
        try {
            File excelFile = clanService.generateClanExcel(tag);
            return "Arquivo Excel criado com sucesso: " + excelFile.getAbsolutePath();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
