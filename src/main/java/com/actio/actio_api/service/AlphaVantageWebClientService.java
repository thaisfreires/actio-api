package com.actio.actio_api.service;

import com.actio.actio_api.model.webclient.AlphaVantageResponse;
import com.actio.actio_api.model.webclient.GlobalQuote;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * Service responsible for fetching stock quote data and currency exchange rates
 * from the Alpha Vantage API, and converting all relevant monetary fields from USD to EUR.
 *
 * This class performs two operations:
 * 1. Retrieves the current USD to EUR exchange rate from Alpha Vantage.
 * 2. Fetches the latest stock quote for a given symbol and applies the exchange rate.
 *
 * It uses Spring WebClient for HTTP communication and returns data in reactive Mono format.
 */
@Service
public class AlphaVantageWebClientService {

    private final WebClient webClient;

    @Value("${apiKey}")
    private String apiKey;

    public AlphaVantageWebClientService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://www.alphavantage.co").build();
    }

    /**
     * Fetches the latest stock quote for a given symbol and converts the financial values to EUR.
     *
     * @param symbol the stock symbol (e.g. "BCP.LS" or "NOS.LS")
     * @return Mono emitting the converted GlobalQuote object
     */
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

    /**
     * Multiplies monetary values in the stock quote by the given USD→EUR exchange rate.
     *
     * @param quote the original GlobalQuote object with USD values
     * @param exchangeRate the USD to EUR exchange rate to apply
     * @return GlobalQuote with values converted to EUR
     */
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

    /**
     * Retrieves the USD to EUR exchange rate from the Alpha Vantage API.
     * Validates the response and parses the rate as BigDecimal.
     *
     * @return Mono emitting the exchange rate as BigDecimal
     */
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
