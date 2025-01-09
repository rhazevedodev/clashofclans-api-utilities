package com.br.natividade.api.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/clanRaids")
public class ClanRaidsController {

    @GetMapping("/endpoint")
    public String getClanRaids() {
        String API_URL = "https://api.clashofclans.com/v1/clans/";
        String BEARER_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjgwZGVlNmMyLTgxNzItNGFlZi1hZTE5LTJkZjMwY2Y1MGZmZiIsImlhdCI6MTcxNjk0MTQ0OCwic3ViIjoiZGV2ZWxvcGVyL2EwODJkMGU5LTVjNDQtMmVlMi1hY2ZlLTBlN2E4MzEyNjM4NyIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE3OS4yMDkuMTQwLjExNyJdLCJ0eXBlIjoiY2xpZW50In1dfQ.Vp9LuSvB5Xe7RNIl80IoVumgtcscrwBhMw3negpgtgcu-tdFIZqFTZD39ER54Jls4zudCrdxonXWh6LgyKHGIQ";
        String tagNatividade = "#2YJRLYYCC";
        String encodedTag = tagNatividade.replace("#", "%23");
        String url = API_URL + encodedTag + "/capitalraidseasons";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + BEARER_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);


        return "Clan Raids";
    }



}
