package com.br.natividade.api.controller;

import com.br.natividade.api.helper.HttpUtil;
import com.br.natividade.api.model.Clan;
import com.br.natividade.api.model.ContaEnum;
import com.br.natividade.api.model.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    private static final String API_URL = "https://api.clashofclans.com/v1/clans/";
    private static final String BEARER_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjgwZGVlNmMyLTgxNzItNGFlZi1hZTE5LTJkZjMwY2Y1MGZmZiIsImlhdCI6MTcxNjk0MTQ0OCwic3ViIjoiZGV2ZWxvcGVyL2EwODJkMGU5LTVjNDQtMmVlMi1hY2ZlLTBlN2E4MzEyNjM4NyIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE3OS4yMDkuMTQwLjExNyJdLCJ0eXBlIjoiY2xpZW50In1dfQ.Vp9LuSvB5Xe7RNIl80IoVumgtcscrwBhMw3negpgtgcu-tdFIZqFTZD39ER54Jls4zudCrdxonXWh6LgyKHGIQ";


    @GetMapping("/test")
    public String testEndpoint() {
        return "Hello, this is a test endpoint!";
    }

    @GetMapping("/endpoint")
    public String testEndpoint2() {
        String tag = "#20P292C8Y";
        String tagNatividade = "#2YJRLYYCC";
        String encodedTag = tag.replace("#", "%23");
        String url = API_URL + encodedTag;

        HttpRequest request = HttpUtil.createRequest(url, BEARER_TOKEN);
        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Clan clan = objectMapper.readValue(response.body(), Clan.class);
            List<Member> memberList = clan.memberList();

            // Crie uma pasta de trabalho e três planilhas
            Workbook workbook = new XSSFWorkbook();

            // Planilha para contas principais
            Sheet primarySheet = workbook.createSheet("Contas Principais");
            createHeaderRow(primarySheet, 0, false);
            int primaryRowNum = 1;

            // Planilha para contas secundárias
            Sheet secondarySheet = workbook.createSheet("Contas Secundárias");
            createHeaderRow(secondarySheet, 0, true);
            int secondaryRowNum = 1;

            // Planilha para contas não identificadas
            Sheet unidentifiedSheet = workbook.createSheet("Contas Não Identificadas");
            createHeaderRow(unidentifiedSheet, 0, false);
            int unidentifiedRowNum = 1;

            // Crie um mapa de ContaEnum para facilitar a busca
            Map<String, ContaEnum> contaMap = Arrays.stream(ContaEnum.values())
                    .collect(Collectors.toMap(ContaEnum::getTag, conta -> conta));

            // Popule as planilhas com dados da memberList
            for (Member member : memberList) {
                ContaEnum conta = contaMap.get(member.tag());

                if (conta != null) {
                    if (conta.getTagContaPrincipal().isEmpty()) {
                        // Contas principais
                        Row row = primarySheet.createRow(primaryRowNum++);
                        row.createCell(0).setCellValue(member.tag());
                        row.createCell(1).setCellValue(member.name());
                        row.createCell(2).setCellValue(member.townHallLevel());
                    } else {
                        // Contas secundárias
                        Row row = secondarySheet.createRow(secondaryRowNum++);
                        row.createCell(0).setCellValue(member.tag());
                        row.createCell(1).setCellValue(member.name());
                        row.createCell(2).setCellValue(member.townHallLevel());
                        row.createCell(3).setCellValue(ContaEnum.getEnumNameByTag(conta.getTagContaPrincipal()));
                    }
                } else {
                    // Contas não identificadas
                    Row row = unidentifiedSheet.createRow(unidentifiedRowNum++);
                    row.createCell(0).setCellValue(member.tag());
                    row.createCell(1).setCellValue(member.name());
                    row.createCell(2).setCellValue(member.townHallLevel());
                }
            }

            // Defina um caminho no sistema de arquivos para salvar o arquivo Excel
            File outputDir = new File(System.getProperty("java.io.tmpdir"), "generatedExcels");
            if (!outputDir.exists()) {
                outputDir.mkdirs(); // Cria o diretório se ele não existir
            }

            // Salvar o arquivo no diretório especificado
            File outputFile = new File(outputDir, "clan_members_" + clan.name() + ".xlsx");
            try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
                workbook.write(fileOut);
            }

            workbook.close();

            return "Arquivo Excel criado com sucesso: " + outputFile.getAbsolutePath();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Método auxiliar para criar o cabeçalho da planilha
    private void createHeaderRow(Sheet sheet, int startCol, boolean isSecundaria) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(startCol).setCellValue("Tag");
        headerRow.createCell(startCol + 1).setCellValue("Name");
        headerRow.createCell(startCol + 2).setCellValue("TownHall Level");
        if(isSecundaria) {
            headerRow.createCell(startCol + 3).setCellValue("Conta Principal");
        }
    }
}
