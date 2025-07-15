package com.actio.actio_api.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
public class CurrencyCacheService {

    private final WebClient webClient;

    public CurrencyCacheService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://www.alphavantage.co").build();
    }

    /**
     * Retrieves and caches the USD to EUR exchange rate using the Alpha Vantage API.
     *
     * This method makes a synchronous HTTP request to the Alpha Vantage endpoint
     * using the provided API key and extracts the current exchange rate.
     * If the response indicates an error (rate limit exceeded, invalid key)
     * or contains an unexpected structure, a predefined fallback value is returned instead.
     *
     * Caching is enabled via Spring's @Cacheable annotation, allowing reuse of responses
     * across the application. Cached entries are keyed based on the API key value.
     * Use of .block() is intentional to support synchronous caching requirements.
     *
     * Network errors, malformed responses, or missing exchange rate values
     * are handled gracefully with logging and fallback logic.
     *
     * @param apiKey the Alpha Vantage API key to authenticate the request
     * @return the USD to EUR exchange rate as a BigDecimal,
     *         or a fallback value if the API request fails or rate is missing
     */
    @Cacheable(value = "usdToEurRate")
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
                    System.out.println("[CurrencyCacheService] Alpha Vantage response:\n" + json.toPrettyString());

                    if (json.has("Note") || json.has("Information")) {
                        System.out.println("[CurrencyCacheService] Exchange rate NOT retrieved from API " +
                                "(rate limit exceeded or invalid key)");
                        return getFallbackRate();
                    }

                    JsonNode rateNode = json.path("Realtime Currency Exchange Rate").path("5. Exchange Rate");
                    if (rateNode.isMissingNode() || rateNode.asText().isBlank()) {
                        System.out.println("[CurrencyCacheService] Exchange rate field NOT found in API response");
                        return getFallbackRate();
                    }

                    System.out.println("[CurrencyCacheService] Exchange rate retrieved successfully from API");
                    return new BigDecimal(rateNode.asText());
                })
                .onErrorResume(ex -> {
                    System.out.println("[CurrencyCacheService] Error during API call: " + ex.getMessage());
                    System.out.println("[CurrencyCacheService] Falling back to local exchange rate");
                    return Mono.just(getFallbackRate());
                })
                .block();
    }

    /**
     * Returns a fallback exchange rate when Alpha Vantage is unavailable or the response is invalid.
     */
    private BigDecimal getFallbackRate() {
        BigDecimal fallback = BigDecimal.valueOf(0.90);
        System.out.println("[CurrencyCacheService] Using fallback exchange rate: " + fallback);
        return fallback;
    }

}
