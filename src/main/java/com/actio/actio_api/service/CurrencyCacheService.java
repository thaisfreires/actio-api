package com.actio.actio_api.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

/**
 * Service responsible for retrieving and caching the USD to EUR currency exchange rate
 * from the Alpha Vantage API using Spring WebClient.
 *
 * The exchange rate is cached for performance and reliability, reducing external API calls
 * and ensuring consistent data usage across multiple stock quote requests.
 *
 * This service is designed to be injected into other components that require access to
 * real-time currency conversion.
 */
@Service
@RequiredArgsConstructor
public class CurrencyCacheService {

    private final WebClient webClient;

    /**
     * Retrieves the USD to EUR exchange rate from the Alpha Vantage API.
     * The result is cached using the cache named "usdToEurRate" to avoid repeated external calls.
     *
     * If the rate cannot be extracted from the response, an IllegalStateException is thrown.
     * The use of .block() is intentional here, as caching via @Cacheable requires synchronous return.
     *
     * @param apiKey API key used for authentication with Alpha Vantage
     * @return the exchange rate as a BigDecimal
     */
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
                }).block();
    }
}
