package com.br.natividade.api.helper;

import com.br.natividade.api.model.ClanWarLeagueWarAttacks;
import com.br.natividade.api.model.ClanWarLeagueWarMembers;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExcelExporter3 {
    public void exportToExcel(List<ClanWarLeagueWarMembers> firstList,
                              List<ClanWarLeagueWarMembers> secondList,
                              List<ClanWarLeagueWarMembers> thirdList,
                              List<ClanWarLeagueWarMembers> fourthList,
                              List<ClanWarLeagueWarMembers> fifthList,
                              List<ClanWarLeagueWarMembers> sixthList,
                              List<ClanWarLeagueWarMembers> seventhList,
                              String filePath) {

        // Ordenar as listas de membros
        List<List<ClanWarLeagueWarMembers>> lists = List.of(firstList, secondList, thirdList, fourthList, fifthList, sixthList, seventhList);
        for (List<ClanWarLeagueWarMembers> list : lists) {
            Collections.sort(list, Comparator.comparing(ClanWarLeagueWarMembers::mapPosition));
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Members Data");

            // Criar estilo para centralização
            CellStyle centeredStyle = workbook.createCellStyle();
            centeredStyle.setAlignment(HorizontalAlignment.CENTER);
            centeredStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            int startColumn = 0;

            // Criar cabeçalhos
            Row headerRow1 = sheet.createRow(0);
            Row headerRow2 = sheet.createRow(1);

            for (int i = 0; i < 7; i++) {
                int currentColumn = startColumn + i * 4; // Cada grupo de colunas tem 4 colunas

                // Mesclar células para cabeçalhos
                sheet.addMergedRegion(new CellRangeAddress(0, 0, currentColumn, currentColumn + 1)); // Guerra X (por exemplo, Guerra 1, Guerra 2, etc.)

                // Definir cabeçalhos
                Cell cell = headerRow1.createCell(currentColumn);
                cell.setCellValue("Guerra " + (i + 1));
                cell.setCellStyle(centeredStyle);

                // Definir sub-cabeçalhos
                cell = headerRow2.createCell(currentColumn);
                cell.setCellValue("Ataque");
                cell.setCellStyle(centeredStyle);

                cell = headerRow2.createCell(currentColumn + 1);
                cell.setCellValue("Defesa");
                cell.setCellStyle(centeredStyle);

                // Preencher dados da lista correspondente
                List<ClanWarLeagueWarMembers> currentList = lists.get(i);
                int rowNum = 2;
                for (ClanWarLeagueWarMembers member : currentList) {
                    Row row = sheet.createRow(rowNum++);

                    // Preencher a tag e nome, começando na coluna da lista atual
                    Cell cellTag = row.createCell(currentColumn);
                    cellTag.setCellValue(member.tag());
                    cellTag.setCellStyle(centeredStyle);

                    Cell cellName = row.createCell(currentColumn + 1);
                    cellName.setCellValue(member.name());
                    cellName.setCellStyle(centeredStyle);

                    // Preencher estrelas dos ataques e melhor ataque do oponente
                    StringBuilder attackStars = new StringBuilder();
                    if (member.attacks() != null) {
                        for (ClanWarLeagueWarAttacks attack : member.attacks()) {
                            attackStars.append(attack.stars()).append(" ");
                        }
                    } else {
                        attackStars.append("No attacks found.");
                    }
                    Cell cellAttackStars = row.createCell(currentColumn + 2);
                    cellAttackStars.setCellValue(attackStars.toString().trim());
                    cellAttackStars.setCellStyle(centeredStyle);

                    int bestAttackStars = member.bestOpponentAttack() != null ?
                            member.bestOpponentAttack().stars() : 1;
                    Cell cellBestAttackStars = row.createCell(currentColumn + 3);
                    cellBestAttackStars.setCellValue(bestAttackStars);
                    cellBestAttackStars.setCellStyle(centeredStyle);
                }
            }

            // Ajustar o tamanho das colunas para caber o conteúdo
            int totalColumns = 7 * 4;
            for (int i = 0; i < totalColumns; i++) {
                sheet.autoSizeColumn(i);
            }

            // Escrever o arquivo
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            System.out.println("Excel file created at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

