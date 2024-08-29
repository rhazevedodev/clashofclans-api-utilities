package com.br.natividade.api.service;

import com.br.natividade.api.helper.HttpUtil;
import com.br.natividade.api.model.Clan;
import com.br.natividade.api.model.ContaEnum;
import com.br.natividade.api.model.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

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

@Service
public class ClanService {

    private static final String API_URL = "https://api.clashofclans.com/v1/clans/";
    private static final String BEARER_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjgwZGVlNmMyLTgxNzItNGFlZi1hZTE5LTJkZjMwY2Y1MGZmZiIsImlhdCI6MTcxNjk0MTQ0OCwic3ViIjoiZGV2ZWxvcGVyL2EwODJkMGU5LTVjNDQtMmVlMi1hY2ZlLTBlN2E4MzEyNjM4NyIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE3OS4yMDkuMTQwLjExNyJdLCJ0eXBlIjoiY2xpZW50In1dfQ.Vp9LuSvB5Xe7RNIl80IoVumgtcscrwBhMw3negpgtgcu-tdFIZqFTZD39ER54Jls4zudCrdxonXWh6LgyKHGIQ";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ClanService(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public File generateClanExcel(String tag) throws IOException, InterruptedException {
        String encodedTag = tag.replace("#", "%23");
        String url = API_URL + encodedTag;

        HttpRequest request = HttpUtil.createRequest(url, BEARER_TOKEN);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Clan clan = objectMapper.readValue(response.body(), Clan.class);
        List<Member> memberList = clan.memberList();

        Workbook workbook = new XSSFWorkbook();
        Sheet primarySheet = workbook.createSheet("Contas Principais");
        createHeaderRow(primarySheet, 0, false);
        int primaryRowNum = 1;

        Sheet secondarySheet = workbook.createSheet("Contas Secundárias");
        createHeaderRow(secondarySheet, 0, true);
        int secondaryRowNum = 1;

        Sheet unidentifiedSheet = workbook.createSheet("Contas Não Identificadas");
        createHeaderRow(unidentifiedSheet, 0, false);
        int unidentifiedRowNum = 1;

        Map<String, ContaEnum> contaMap = Arrays.stream(ContaEnum.values())
                .collect(Collectors.toMap(ContaEnum::getTag, conta -> conta));

        for (Member member : memberList) {
            ContaEnum conta = contaMap.get(member.tag());

            if (conta != null) {
                if (conta.getTagContaPrincipal().isEmpty()) {
                    Row row = primarySheet.createRow(primaryRowNum++);
                    row.createCell(0).setCellValue(member.tag());
                    row.createCell(1).setCellValue(member.name());
                    row.createCell(2).setCellValue(member.townHallLevel());
                } else {
                    Row row = secondarySheet.createRow(secondaryRowNum++);
                    row.createCell(0).setCellValue(member.tag());
                    row.createCell(1).setCellValue(member.name());
                    row.createCell(2).setCellValue(member.townHallLevel());
                    row.createCell(3).setCellValue(ContaEnum.getEnumNameByTag(conta.getTagContaPrincipal()));
                }
            } else {
                Row row = unidentifiedSheet.createRow(unidentifiedRowNum++);
                row.createCell(0).setCellValue(member.tag());
                row.createCell(1).setCellValue(member.name());
                row.createCell(2).setCellValue(member.townHallLevel());
            }
        }

        File outputDir = new File(System.getProperty("java.io.tmpdir"), "generatedExcels");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File outputFile = new File(outputDir, "clan_members_" + clan.name() + ".xlsx");
        try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
            workbook.write(fileOut);
        }

        workbook.close();
        return outputFile;
    }

    private void createHeaderRow(Sheet sheet, int startCol, boolean isSecundaria) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(startCol).setCellValue("Tag");
        headerRow.createCell(startCol + 1).setCellValue("Name");
        headerRow.createCell(startCol + 2).setCellValue("TownHall Level");
        if (isSecundaria) {
            headerRow.createCell(startCol + 3).setCellValue("Conta Principal");
        }
    }
}
