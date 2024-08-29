//package com.br.natividade.api.service;
//
//import com.br.natividade.api.helper.ExcelExporter;
//import com.br.natividade.api.helper.ExcelExporter2;
//import com.br.natividade.api.helper.ExcelExporter3;
//import com.br.natividade.api.model.*;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.List;
//
//public class ApiService {
//
//    private static final String API_URL_PARTE1 = "https://api.clashofclans.com/v1/clans/";
//    private static final String API_URL_PARTE2 = "/currentwar/leaguegroup";
//    private static final String BEARER_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjgwZGVlNmMyLTgxNzItNGFlZi1hZTE5LTJkZjMwY2Y1MGZmZiIsImlhdCI6MTcxNjk0MTQ0OCwic3ViIjoiZGV2ZWxvcGVyL2EwODJkMGU5LTVjNDQtMmVlMi1hY2ZlLTBlN2E4MzEyNjM4NyIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE3OS4yMDkuMTQwLjExNyJdLCJ0eXBlIjoiY2xpZW50In1dfQ.Vp9LuSvB5Xe7RNIl80IoVumgtcscrwBhMw3negpgtgcu-tdFIZqFTZD39ER54Jls4zudCrdxonXWh6LgyKHGIQ";
//    private static final String API_URL2 = "https://api.clashofclans.com/v1/clanwarleagues/wars/";
//
//    public static void main(String[] args) {
//        System.out.println("Hello, World!");
//
//        String tag = "#2YJRLYYCC";
//        String encodedTag = tag.replace("#", "%23");
//
//        try {
//            //REQUISICAO 1 - INICIO
//            //MONTA URL COM O PARAMETRO TAG
//            String url = API_URL_PARTE1 + encodedTag + API_URL_PARTE2;
//
//            //CONFIGURA O HTTPCLIENT
//            HttpClient client = HttpClient.newHttpClient();
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(url))
//                    .header("Authorization", "Bearer " + BEARER_TOKEN)
//                    .GET()
//                    .build();
//
//            //FAZ A REQUISICAO E CAPTURA A RESPOSTA
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//            ClanWarLeagueGroup clanWarLeagueGroup = null;
//            List<ClanWarLeagueRound> rounds = null;
//            //VERIFICAR O STATUS DA RESPOSTA
//            if(response.statusCode() == 200) {
//                // Exibe a resposta JSON
////                System.out.println("Resposta da API: " + response.body());
//                ObjectMapper objectMapper = new ObjectMapper();
//                clanWarLeagueGroup = objectMapper.readValue(response.body(), ClanWarLeagueGroup.class);
//
//                // Exibe informações da resposta
//                String state = clanWarLeagueGroup.state();
//                rounds = clanWarLeagueGroup.rounds();
//
////                System.out.println("State: " + state);
//
////                List<String> dia1Tags = rounds.get(0).warTags();
//                List<String> dia2Tags = rounds.get(1).warTags();
//                List<String> dia3Tags = rounds.get(2).warTags();
//                List<String> dia4Tags = rounds.get(3).warTags();
//                List<String> dia5Tags = rounds.get(4).warTags();
//                List<String> dia6Tags = rounds.get(5).warTags();
//                List<String> dia7Tags = rounds.get(6).warTags();
////                System.out.println("TAGS DIA 1: "+dia1Tags);
////                System.out.println("TAGS DIA 2: "+dia2Tags);
////                System.out.println("TAGS DIA 3: "+dia3Tags);
////                System.out.println("TAGS DIA 4: "+dia4Tags);
////                System.out.println("TAGS DIA 5: "+dia5Tags);
////                System.out.println("TAGS DIA 6: "+dia6Tags);
////                System.out.println("TAGS DIA 7: "+dia7Tags);
//
////                int maxIterations = 8;
////                int count = 1;
////                for(ClanWarLeagueRound round : rounds) {
////                    if (count >= maxIterations) {
////                        break; // Sai do loop após atingir o número máximo de iterações
////                    }
////                    List<String> warTags = round.warTags();
////                    System.out.println("War Tags [Dia "+count+"]: " + warTags);
////                    count++; // Incrementa o contador
////                }
//
//            } else {
//                System.out.println("Erro: " + response.statusCode() + " - " + response.body());
//            }
//            //REQUISICAO 1 - FIM
//            //REQUISICAO 2 - INICIO
//            // Lista de tags para iterar
//            List<String> tags = rounds.get(0).warTags();
//            System.out.println("TAGS DA SEGUNDA REQUISICAO: "+tags);
//
//            HttpClient client2 = HttpClient.newHttpClient();
//            boolean found = false;
//
//            ClanWarLeagueWarRegistry clanWarLeagueWarRegistry = null;
//            for (String tagHere : tags) {
//                String encodedTag2 = tagHere.replace("#", "%23");
//                String url2 = API_URL2 + encodedTag2;
//
//                try {
//                    // Monta e envia a requisição HTTP
//                    HttpRequest request2 = HttpRequest.newBuilder()
//                            .uri(URI.create(url2))
//                            .header("Authorization", "Bearer " + BEARER_TOKEN)
//                            .GET()
//                            .build();
//
//                    HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());
//
//                    // Verifica o status da resposta
//                    if (response2.statusCode() == 200) {
//                        String responseBody = response2.body();
//                        // Verifica se o conteúdo contém "NATIVIDADE"
//                        if (responseBody.contains("NATIVIDADE")) {
////                            System.out.println("Conteúdo encontrado na tag: " + tagHere);
////                            System.out.println("Resposta da API2: " + responseBody);
//                            found = true;
//                            ObjectMapper objectMapper = new ObjectMapper();
//                            clanWarLeagueWarRegistry = objectMapper.readValue(response2.body(), ClanWarLeagueWarRegistry.class);
//                        }
//                    } else {
//                        System.out.println("Erro2: " + response2.statusCode() + " - " + response2.body());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (!found) {
//                System.out.println("Nenhuma das tags retornou conteúdo contendo 'NATIVIDADE'.");
//            }
//
//            //REQUISICAO 2 - FIM
//            //REQUISICAO 3 - INICIO
//            //
//            ClanWarLeagueWarClan meuClan =
//                    clanWarLeagueWarRegistry.clan().name().equals("NATIVIDADE") ?
//                            clanWarLeagueWarRegistry.clan() :
//                            clanWarLeagueWarRegistry.opponent();
//
//
//            for (ClanWarLeagueWarMembers member : meuClan.members()) {
////                System.out.println("Nome: " + member.name());
////                System.out.println("Tag: " + member.tag());
////                System.out.println("Map Position: " + member.mapPosition());
////                if (member.attacks() != null) {
////                    for (ClanWarLeagueWarAttacks attack : member.attacks()) {
////                        System.out.println("Attack Stars: " + attack.stars());
////                    }
////                } else {
////                    System.out.println("Attack Stars: No attacks found.");
////                }
////                if(member.bestOpponentAttack() != null){
////                    System.out.println("Best Opponent Attack Stars: " + member.bestOpponentAttack().stars());
////                } else {
////                    int defaultValue = 1;
////                    System.out.println("Best Opponent Attack Stars:" + defaultValue);
////                }
////                System.out.println("______________________________");
//            }
//            ExcelExporter2 exporter = new ExcelExporter2();
//            exporter.exportToExcel(
//                    meuClan.members(),
//                    meuClan.members(),
//                    meuClan.members(),
//                    meuClan.members(),
//                    meuClan.members(),
//                    meuClan.members(),
//                    meuClan.members(),
//                    "MembersData33.xlsx");
//            //REQUISICAO 3 - FIM
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//}
