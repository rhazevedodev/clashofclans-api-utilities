package com.br.natividade.api.helper;

import com.br.natividade.api.model.ClanWarLeagueWarAttacks;
import com.br.natividade.api.model.ClanWarLeagueWarMembers;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {
    public void exportToExcel(List<ClanWarLeagueWarMembers> members, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Members Data");

            // Criar estilo para centralização
            CellStyle centeredStyle = workbook.createCellStyle();
            centeredStyle.setAlignment(HorizontalAlignment.CENTER);
            centeredStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Criar cabeçalhos
            Row headerRow1 = sheet.createRow(0);
            Row headerRow2 = sheet.createRow(1);

            // Mesclar células
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0)); // Tag (A1:A2)
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1)); // Nome (B1:B2)
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 3)); // Guerra 1 (C1:D1)

            // Definir os cabeçalhos
            Cell cell;
            cell = headerRow1.createCell(0);
            cell.setCellValue("Tag");
            cell.setCellStyle(centeredStyle);

            cell = headerRow1.createCell(1);
            cell.setCellValue("Nome");
            cell.setCellStyle(centeredStyle);

            cell = headerRow1.createCell(2);
            cell.setCellValue("Guerra 1");
            cell.setCellStyle(centeredStyle);

            // Definir sub-cabeçalhos
            cell = headerRow2.createCell(2);
            cell.setCellValue("Ataque");
            cell.setCellStyle(centeredStyle);

            cell = headerRow2.createCell(3);
            cell.setCellValue("Defesa");
            cell.setCellStyle(centeredStyle);

            // Deixar as células de baixo em branco, já que foram mescladas
            headerRow2.createCell(0);
            headerRow2.createCell(1);

            // Preencher dados
            int rowNum = 2;
            for (ClanWarLeagueWarMembers member : members) {
                Row row = sheet.createRow(rowNum++);
                Cell cellTag = row.createCell(0);
                cellTag.setCellValue(member.tag());
                cellTag.setCellStyle(centeredStyle);

                Cell cellName = row.createCell(1);
                cellName.setCellValue(member.name());

                // Preencher estrelas dos ataques e melhor ataque do oponente
                StringBuilder attackStars = new StringBuilder();
                if (member.attacks() != null) {
                    for (ClanWarLeagueWarAttacks attack : member.attacks()) {
                        attackStars.append(attack.stars()).append(" ");
                    }
                } else {
                    attackStars.append("No attacks found.");
                }
                Cell cellAttackStars = row.createCell(2);
                cellAttackStars.setCellValue(attackStars.toString().trim());
                cellAttackStars.setCellStyle(centeredStyle);

                int bestAttackStars = member.bestOpponentAttack() != null ?
                        member.bestOpponentAttack().stars() : 1;
                Cell cellBestAttackStars = row.createCell(3);
                cellBestAttackStars.setCellValue(bestAttackStars);
                cellBestAttackStars.setCellStyle(centeredStyle);
            }

            // Ajustar o tamanho das colunas para caber o conteúdo
            for (int i = 0; i < 4; i++) {
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
