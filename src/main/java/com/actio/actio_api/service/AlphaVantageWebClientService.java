package com.actio.actio_api.service;

import com.actio.actio_api.model.webclient.AlphaVantageResponse;
import com.actio.actio_api.model.webclient.GlobalQuote;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AlphaVantageWebClientService {

    private final WebClient webClient;

    @Value("${apiKey}")
    private String apiKey;

    public AlphaVantageWebClientService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://www.alphavantage.co").build();
    }

    public Mono<GlobalQuote> getStock(String symbol) {
        Double usdEur = getDolarEuroExchangeRate();
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
                .map(globalQuote -> mapGlobalQuoteToEuro(globalQuote, usdEur));
    }

    private GlobalQuote mapGlobalQuoteToEuro(GlobalQuote quote, Double usdEuro) {
         return GlobalQuote.builder()
                .low(quote.getLow() * usdEuro)
                 .open(quote.getOpen() * usdEuro)
                 .price(quote.getPrice())
                 .high(quote.getHigh() * usdEuro)
                 .previousClose(quote.getPreviousClose() * usdEuro)
                 .volume(quote.getVolume())
                 .symbol(quote.getSymbol())
                 .changePercent(quote.getChangePercent())
                 .latestTradingDay(quote.getLatestTradingDay())
                 .change(quote.getChange())
                 .build();
    }

    private Double getDolarEuroExchangeRate() {
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
                    String raw = json.path("Realtime Currency Exchange Rate").path("5. Exchange Rate").asText();
                    return Double.parseDouble(raw);
                }).block();
    }

}
