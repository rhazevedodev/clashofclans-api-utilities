package com.br.natividade.api.controller;

import com.br.natividade.api.exception.CustomException;
import com.br.natividade.api.model.ClanWarLeagueGroup;
import com.br.natividade.api.model.ClanWarLeagueWarRegistry;
import com.br.natividade.api.service.ClanWarLeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/warLeague")
public class ClanWarLeagueController {

    @Autowired
    private ClanWarLeagueService clanWarLeagueService;

//    @GetMapping
//    public String testEndpoint() {
//        return "Hello, this is a test endpoint!";
//    }

    @GetMapping("/obterRounds")
    public ClanWarLeagueGroup getClanWarLeagueData(@RequestParam String tag) {
        try {
            return clanWarLeagueService.fetchClanWarLeagueData(tag);
        } catch (Exception e) {
            throw new CustomException(e.getMessage() +" "+ tag, e);
        }
    }

    @GetMapping("/obterRoundDia")
    public ClanWarLeagueWarRegistry getClanWarLeagueDataRound(@RequestParam String tag, @RequestParam String dia) {
        try {
            return clanWarLeagueService.fetchClanWarLeagueWarRegistry(tag, Integer.parseInt(dia));
        } catch (Exception e) {
            throw new CustomException(e.getMessage() +" "+ tag, e);
        }
    }

    @GetMapping("/exportLeagueFile")
    public List<String> exportLeagueFile() {
        try {
            List<String> tags = Arrays.asList("%232YJRLYYCC", "%2320P292C8Y", "%232QCVR0P2R");
            List<String> filePaths = new ArrayList<>();
            for (String tag : tags) {
                File excelFile = clanWarLeagueService.exportExcelFileAllSevenDaysOfLeague(tag);
                filePaths.add(excelFile.getAbsolutePath());
                System.out.println("Excel file exported to: " + excelFile.getAbsolutePath());
            }
            return filePaths;
        } catch (Exception e) {
            throw new RuntimeException("Error exporting Clan War League data", e);
        }
    }

    @GetMapping("/verificarAtaquesPendentes")
    public String verificarAtaquesPendentes(@RequestParam String tag, @RequestParam String dia) {
        try {
            return clanWarLeagueService.verificarAtaquesPendentes(tag, Integer.parseInt(dia));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter ataques pendentes", e);
        }
    }
}
