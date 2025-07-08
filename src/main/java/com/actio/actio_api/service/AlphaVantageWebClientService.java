package com.actio.actio_api.service;

import com.actio.actio_api.model.response.AlphaVantageResponse;
import com.actio.actio_api.model.webclient.GlobalQuote;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class AlphaVantageWebClientService {

    private final WebClient webClient;

    @Value("${apiKey}")
    private String apiKey;

    public AlphaVantageWebClientService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://www.alphavantage.co").build();
    }

    public Mono<GlobalQuote> getStock(String symbol) {
        BigDecimal usdToEurRate;
        try {
            usdToEurRate = getDolarEuroExchangeRate().block();
        } catch (Exception ex) {
            return Mono.error(new IllegalStateException("Failed to retrieve exchange rate: " + ex.getMessage()));
        }

        if (usdToEurRate == null) {
            return Mono.error(new IllegalStateException("Exchange rate came back null"));
        }

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "GLOBAL_QUOTE")
                        .queryParam("symbol", symbol)
                        .queryParam("apikey", apiKey)
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AlphaVantageResponse.class)
                .map(AlphaVantageResponse::getGlobalQuote)
                .map(globalQuote -> mapGlobalQuoteToEuro(globalQuote, usdToEurRate))
                .onErrorResume(ex -> Mono.error(
                        new IllegalStateException("Error fetching or converting stock data: " + ex.getMessage()
                        )));
    }

    private GlobalQuote mapGlobalQuoteToEuro(GlobalQuote quote, BigDecimal exchangeRate) {
        return GlobalQuote.builder()
                .low(quote.getLow().multiply(exchangeRate))
                .open(quote.getOpen().multiply(exchangeRate))
                .price(quote.getPrice().multiply(exchangeRate))
                .high(quote.getHigh().multiply(exchangeRate))
                .previousClose(quote.getPreviousClose().multiply(exchangeRate))
                .volume(quote.getVolume())
                .symbol(quote.getSymbol())
                .changePercent(quote.getChangePercent())
                .latestTradingDay(quote.getLatestTradingDay())
                .change(quote.getChange().multiply(exchangeRate))
                .build();
    }

    private Mono<BigDecimal> getDolarEuroExchangeRate() {
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
                });
    }

}
