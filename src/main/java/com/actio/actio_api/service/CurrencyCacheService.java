package com.actio.actio_api.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CurrencyCacheService {

    private final WebClient webClient;

    @Cacheable("usdToEurRate")
    public BigDecimal getUsdToEurRate(String apiKey) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "CURRENCY_EXCHANGE_RATE")
                        .queryParam("from_currency", "USD")
                        .queryParam("to_currency", "EUR")
                        .queryParam("apikey", apiKey)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> {
                    String rawRate = json.path("Realtime Currency Exchange Rate").path("5. Exchange Rate").asText();
                    if (rawRate == null || rawRate.isEmpty()) {
                        throw new IllegalStateException("Exchange rate not found");
                    }
                    return new BigDecimal(rawRate);
                }).block(); // cache funciona aqui porque proxy intercepta externamente
    }
}
