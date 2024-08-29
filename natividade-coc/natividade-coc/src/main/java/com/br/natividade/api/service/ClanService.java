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
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${clashofclans.api.url-getClanMembersList}")
    private String apiUrl;

    @Value("${clashofclans.api.bearer-token}")
    private String bearerToken;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ClanService(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public File generateClanExcel(String tag) throws IOException, InterruptedException {
        Clan clan = fetchClanData(tag);
        Map<String, ContaEnum> contaMap = mapContas();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet primarySheet = createSheet(workbook, "Contas Principais", false);
            Sheet secondarySheet = createSheet(workbook, "Contas Secundárias", true);
            Sheet unidentifiedSheet = createSheet(workbook, "Contas Não Identificadas", false);

            fillSheets(clan.memberList(), contaMap, primarySheet, secondarySheet, unidentifiedSheet);

            return writeWorkbookToFile(workbook, clan.name());
        }
    }

    private Clan fetchClanData(String tag) throws IOException, InterruptedException {
        String encodedTag = tag.replace("#", "%23");
        String url = apiUrl + encodedTag;

        HttpRequest request = HttpUtil.createRequest(url, bearerToken);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), Clan.class);
    }

    private Map<String, ContaEnum> mapContas() {
        return Arrays.stream(ContaEnum.values())
                .collect(Collectors.toMap(ContaEnum::getTag, conta -> conta));
    }

    private Sheet createSheet(Workbook workbook, String sheetName, boolean isSecundaria) {
        Sheet sheet = workbook.createSheet(sheetName);
        createHeaderRow(sheet, 0, isSecundaria);
        return sheet;
    }

    private void fillSheets(List<Member> members, Map<String, ContaEnum> contaMap,
                            Sheet primarySheet, Sheet secondarySheet, Sheet unidentifiedSheet) {
        int primaryRowNum = 1;
        int secondaryRowNum = 1;
        int unidentifiedRowNum = 1;

        for (Member member : members) {
            ContaEnum conta = contaMap.get(member.tag());

            Sheet targetSheet;
            int rowNum;
            boolean isSecondary = false;

            if (conta != null) {
                if (conta.getTagContaPrincipal().isEmpty()) {
                    targetSheet = primarySheet;
                    rowNum = primaryRowNum++;
                } else {
                    targetSheet = secondarySheet;
                    rowNum = secondaryRowNum++;
                    isSecondary = true;
                }
            } else {
                targetSheet = unidentifiedSheet;
                rowNum = unidentifiedRowNum++;
            }

            fillRow(targetSheet, rowNum, member, conta, isSecondary);
        }
    }

    private void fillRow(Sheet sheet, int rowNum, Member member, ContaEnum conta, boolean isSecondary) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(member.tag());
        row.createCell(1).setCellValue(member.name());
        row.createCell(2).setCellValue(member.townHallLevel());
        if (isSecondary && conta != null) {
            row.createCell(3).setCellValue(ContaEnum.getEnumNameByTag(conta.getTagContaPrincipal()));
        }
    }

    private File writeWorkbookToFile(Workbook workbook, String clanName) throws IOException {
        File outputDir = new File(System.getProperty("java.io.tmpdir"), "generatedExcels");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File outputFile = new File(outputDir, "clan_members_" + clanName + ".xlsx");
        try (FileOutputStream fileOut = new FileOutputStream(outputFile)) {
            workbook.write(fileOut);
        }

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