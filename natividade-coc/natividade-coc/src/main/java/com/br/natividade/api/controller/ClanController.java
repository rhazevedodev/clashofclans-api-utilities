package com.br.natividade.api.controller;

import com.br.natividade.api.helper.HttpUtil;
import com.br.natividade.api.model.Clan;
import com.br.natividade.api.model.ContaEnum;
import com.br.natividade.api.model.Member;
import com.br.natividade.api.service.ClanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
