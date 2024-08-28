package com.br.natividade.api.helper;

import java.net.URI;
import java.net.http.HttpRequest;

public class HttpUtil {

    public static HttpRequest createRequest(String url, String bearerToken) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + bearerToken)
                .GET()
                .build();
    }
}
