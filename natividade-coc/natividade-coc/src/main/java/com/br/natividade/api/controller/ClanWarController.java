package com.br.natividade.api.controller;

import com.br.natividade.api.helper.HttpUtil;
import com.br.natividade.api.model.ClanWar;
import com.br.natividade.api.model.ClanWarAttacksSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/clanWar")
public class ClanWarController {

    private static final String API_URL = "https://api.clashofclans.com/v1/clans/";
    private static final String BEARER_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjgwZGVlNmMyLTgxNzItNGFlZi1hZTE5LTJkZjMwY2Y1MGZmZiIsImlhdCI6MTcxNjk0MTQ0OCwic3ViIjoiZGV2ZWxvcGVyL2EwODJkMGU5LTVjNDQtMmVlMi1hY2ZlLTBlN2E4MzEyNjM4NyIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE3OS4yMDkuMTQwLjExNyJdLCJ0eXBlIjoiY2xpZW50In1dfQ.Vp9LuSvB5Xe7RNIl80IoVumgtcscrwBhMw3negpgtgcu-tdFIZqFTZD39ER54Jls4zudCrdxonXWh6LgyKHGIQ";

    @GetMapping("/teste")
    public ClanWarAttacksSummary teste() {
        String tagNatividade = "#2YJRLYYCC";
        String encodedTag = tagNatividade.replace("#", "%23");
        String url = API_URL + encodedTag + "/currentwar";

        HttpRequest request = HttpUtil.createRequest(url, BEARER_TOKEN);
        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            ClanWar clanWar = objectMapper.readValue(response.body(), ClanWar.class);

            String jsonResponse = response.body();
            System.out.println("JSON Response: " + jsonResponse);

            JSONObject jsonObject = new JSONObject(jsonResponse);

            JSONArray clans;
            if (jsonObject.has("clan")) {
                Object clanObject = jsonObject.get("clan");
                if (clanObject instanceof JSONArray) {
                    clans = (JSONArray) clanObject;
                } else if (clanObject instanceof JSONObject) {
                    JSONObject clanJson = (JSONObject) clanObject;
                    clans = new JSONArray();
                    clans.put(clanJson);
                } else {
                    throw new RuntimeException("O campo 'clan' está em um formato inesperado.");
                }
            } else {
                throw new RuntimeException("O campo 'clan' não encontrado na resposta.");
            }

            List<String> jogadoresSemAtaques = new ArrayList<>();
            List<String> jogadoresComUmAtaque = new ArrayList<>();
            List<String> jogadoresComDoisAtaques = new ArrayList<>();

            for (int i = 0; i < clans.length(); i++) {
                JSONObject clan = clans.getJSONObject(i);

                if (clan.getString("name").equals("NATIVIDADE_BR")) {
                    JSONArray members = clan.getJSONArray("members");

                    for (int j = 0; j < members.length(); j++) {
                        JSONObject member = members.getJSONObject(j);

                        if (member.has("attacks")) {
                            int attacks = member.getJSONArray("attacks").length();

                            if (attacks == 1) {
                                jogadoresComUmAtaque.add(member.getString("name"));
                            } else if (attacks == 2) {
                                jogadoresSemAtaques.add(member.getString("name"));
                            }
                        } else {
                            jogadoresComDoisAtaques.add(member.getString("name"));
                        }
                    }
                    break;
                }
            }

            ClanWarAttacksSummary summary = new ClanWarAttacksSummary();
            summary.setJogadoresSemAtaquesDisponiveis(jogadoresSemAtaques);
            summary.setJogadoresComUmAtaquesDisponiveis(jogadoresComUmAtaque);
            summary.setJogadoresComDoisAtaquesDisponiveis(jogadoresComDoisAtaques);

            return summary;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao processar a requisição", e);
        }
    }
}