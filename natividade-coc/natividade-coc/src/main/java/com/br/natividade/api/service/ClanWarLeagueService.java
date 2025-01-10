package com.br.natividade.api.service;

import com.br.natividade.api.enumerator.Clans;
import com.br.natividade.api.helper.ExcelExporter2;
import com.br.natividade.api.model.ClanWarLeagueWarClan;
import com.br.natividade.api.model.ClanWarLeagueWarMembers;
import com.br.natividade.api.model.ClanWarLeagueWarRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.br.natividade.api.helper.HttpUtil;
import com.br.natividade.api.model.ClanWarLeagueGroup;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class ClanWarLeagueService {

    private static final String API_URL_PARTE1 = "https://api.clashofclans.com/v1/clans/";
    private static final String API_URL_PARTE2 = "/currentwar/leaguegroup";
    private static final String BEARER_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjgwZGVlNmMyLTgxNzItNGFlZi1hZTE5LTJkZjMwY2Y1MGZmZiIsImlhdCI6MTcxNjk0MTQ0OCwic3ViIjoiZGV2ZWxvcGVyL2EwODJkMGU5LTVjNDQtMmVlMi1hY2ZlLTBlN2E4MzEyNjM4NyIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE3OS4yMDkuMTQwLjExNyJdLCJ0eXBlIjoiY2xpZW50In1dfQ.Vp9LuSvB5Xe7RNIl80IoVumgtcscrwBhMw3negpgtgcu-tdFIZqFTZD39ER54Jls4zudCrdxonXWh6LgyKHGIQ";
    private static final String API_URL2 = "https://api.clashofclans.com/v1/clanwarleagues/wars/";

    public ClanWarLeagueGroup fetchClanWarLeagueData(String tag) throws Exception {
        String encodedTag = tag.replace("#", "%23");
        String url = API_URL_PARTE1 + encodedTag + API_URL_PARTE2;

        // Use HttpUtil to create the HttpRequest
        HttpRequest request = HttpUtil.createRequest(url, BEARER_TOKEN);

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), ClanWarLeagueGroup.class);
        }
        if (response.statusCode() == 404) {
            throw new RuntimeException("Não foi encontrado uma liga de guerras para o clã informado.");
        }
        return null;
    }

    public ClanWarLeagueWarRegistry fetchClanWarLeagueWarRegistry(String tag, int dia) throws Exception {
        // Busca os dados do grupo de guerras
        ClanWarLeagueGroup clanWarLeagueGroup = fetchClanWarLeagueData(tag);
        List<String> tags = clanWarLeagueGroup.rounds().get(dia-1).warTags();
//        System.out.println(tags);

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        for (String tagHere : tags) {
            String encodedTag = tagHere.replace("#", "%23");
            String url = API_URL2 + encodedTag;

            HttpRequest request = HttpUtil.createRequest(url, BEARER_TOKEN);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();
                // Verifica se o conteúdo contém "NATIVIDADE"
                for (Clans clan : Clans.values()) {
                    if (responseBody.contains(clan.name())) {
                        return objectMapper.readValue(responseBody, ClanWarLeagueWarRegistry.class);
                    }
                }
            } else {
                System.out.println("Erro: " + response.statusCode() + " - " + response.body());
            }
        }

        // Caso não encontre o conteúdo desejado
        return null;
    }

    public File exportExcelFileAllSevenDaysOfLeague(String tag) throws Exception {
        ClanWarLeagueWarRegistry warRegistry1 = fetchClanWarLeagueWarRegistry(tag, 1);
        ClanWarLeagueWarClan meuClan1 = containsClanName(warRegistry1.clan().name()) ? warRegistry1.clan() : warRegistry1.opponent();
        String clanName = meuClan1.name();

        ClanWarLeagueWarRegistry warRegistry2 = fetchClanWarLeagueWarRegistry(tag, 2);
        ClanWarLeagueWarClan meuClan2 = (warRegistry2 != null && containsClanName(warRegistry2.clan().name())) ? warRegistry2.clan() : (warRegistry2 != null ? warRegistry2.opponent() : null);

        ClanWarLeagueWarRegistry warRegistry3 = fetchClanWarLeagueWarRegistry(tag, 3);
        ClanWarLeagueWarClan meuClan3 = (warRegistry3 != null && containsClanName(warRegistry3.clan().name())) ? warRegistry3.clan() : (warRegistry3 != null ? warRegistry3.opponent() : null);

        ClanWarLeagueWarRegistry warRegistry4 = fetchClanWarLeagueWarRegistry(tag, 4);
        ClanWarLeagueWarClan meuClan4 = (warRegistry4 != null && containsClanName(warRegistry4.clan().name())) ? warRegistry4.clan() : (warRegistry4 != null ? warRegistry4.opponent() : null);

        ClanWarLeagueWarRegistry warRegistry5 = fetchClanWarLeagueWarRegistry(tag, 5);
        ClanWarLeagueWarClan meuClan5 = (warRegistry5 != null && containsClanName(warRegistry5.clan().name())) ? warRegistry5.clan() : (warRegistry5 != null ? warRegistry5.opponent() : null);

        ClanWarLeagueWarRegistry warRegistry6 = fetchClanWarLeagueWarRegistry(tag, 6);
        ClanWarLeagueWarClan meuClan6 = (warRegistry6 != null && containsClanName(warRegistry6.clan().name())) ? warRegistry6.clan() : (warRegistry6 != null ? warRegistry6.opponent() : null);

        ClanWarLeagueWarRegistry warRegistry7 = fetchClanWarLeagueWarRegistry(tag, 7);
        ClanWarLeagueWarClan meuClan7 = (warRegistry7 != null && containsClanName(warRegistry7.clan().name())) ? warRegistry7.clan() : (warRegistry7 != null ? warRegistry7.opponent() : null);

        ExcelExporter2 excelExporter = new ExcelExporter2();

        int warInPreparation = 0;

        if(warRegistry2.state().equals("preparation")) {
            meuClan2 = meuClan1;
            warInPreparation = 2;
        }
        if(warRegistry3.state().equals("preparation")) {
            meuClan3 = meuClan1;
            warInPreparation = 3;
        }
        if(warRegistry4.state().equals("preparation")) {
            meuClan4 = meuClan1;
            warInPreparation = 4;
        }
        if(warRegistry5.state().equals("preparation")) {
            meuClan5 = meuClan1;
            warInPreparation = 5;
        }
        if(warRegistry6.state().equals("preparation")) {
            meuClan6 = meuClan1;
            warInPreparation = 6;
        }
        if(warRegistry7.state().equals("preparation")) {
            meuClan7 = meuClan1;
            warInPreparation = 7;
        }

        return excelExporter.exportToExcel(meuClan1.members(), meuClan2.members(), meuClan3.members(), meuClan4.members(),
                meuClan5.members(), meuClan6.members(), meuClan7.members(), warInPreparation,clanName);

    }

    private boolean containsClanName(String clanName) {
        for (Clans clan : Clans.values()) {
            if (clanName.contains(clan.name())) {
                return true;
            }
        }
        return false;
    }

    public String verificarAtaquesPendentes(String tag, int dia) throws Exception {
        ClanWarLeagueWarRegistry warRegistry1 = fetchClanWarLeagueWarRegistry(tag, dia);
        ClanWarLeagueWarClan meuClan1 = null;

        for (Clans clan : Clans.values()) {
            if (warRegistry1.clan().name().contains(clan.name())) {
                meuClan1 = warRegistry1.clan();
                break;
            } else if (warRegistry1.opponent().name().contains(clan.name())) {
                meuClan1 = warRegistry1.opponent();
                break;
            }
        }

        if (meuClan1 == null) {
            throw new RuntimeException("Nenhum clã correspondente encontrado.");
        }

        List<ClanWarLeagueWarMembers> members = meuClan1.members();
        StringBuilder membersWithNoAttacks = new StringBuilder();

        for (ClanWarLeagueWarMembers member : members) {
            if (member.attacks() == null) {
                membersWithNoAttacks.append(member.name()).append("\n");
            }
        }

        return membersWithNoAttacks.toString();
    }
}


