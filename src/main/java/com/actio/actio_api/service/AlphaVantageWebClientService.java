package com.actio.actio_api.service;

import com.actio.actio_api.model.Stock;
import com.actio.actio_api.model.response.GetAlphaVantageStockResponse;
import com.actio.actio_api.model.webclient.AlphaVantageResponse;
import com.actio.actio_api.model.webclient.GlobalQuote;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Service class responsible for interacting with the Alpha Vantage API to retrieve stock quotes
 * and currency exchange rates, and to convert stock price data from USD to EUR.
 *
 * This service coordinates three main operations:
 * - Fetches the current USD to EUR exchange rate using the CurrencyCacheService.
 * - Retrieves the latest stock quote for a given symbol from the Alpha Vantage API.
 * - Resolves or creates a local stock record and constructs a unified response object
 *   with all relevant financial data in EUR.
 *
 * WebClient is used for making HTTP requests to the external API, and all operations
 * are managed reactively using Mono.
 *
 * Error handling is implemented throughout the chain to ensure fallback behaviors
 * and graceful degradation in the event of API failures or malformed responses.
 *
 * This service is designed to be injected into web endpoints or other components
 * requiring real-time stock and currency conversion.
 */
@Service
public class AlphaVantageWebClientService {

    private final WebClient webClient;
    private final CurrencyCacheService currencyCacheService;
    private final StockService stockService;

    @Value("${apiKey}")
    private String apiKey;

    public AlphaVantageWebClientService(WebClient.Builder builder, CurrencyCacheService currencyCacheService, StockService stockService) {
        this.webClient = WebClient.builder()
                .baseUrl("https://www.alphavantage.co")
                .build();
        this.currencyCacheService = currencyCacheService;
        this.stockService = stockService;
    }

    /**
     * Retrieves stock data for the given symbol, converted from USD to EUR.
     *
     * This method performs the following steps in a reactive chain:
     * - Fetches the current USD→EUR exchange rate.
     * - Retrieves the raw stock quote for the symbol from Alpha Vantage.
     * - Converts relevant price fields in the quote to EUR.
     * - Resolves or stores the corresponding local Stock entity.
     * - Returns a structured response object with combined data.
     *
     * If any step fails, the resulting error is wrapped in an IllegalStateException.
     *
     * @param symbol the stock ticker symbol to query (e.g. "AAPL", "NOS.LS")
     * @return Mono containing the stock quote response in EUR, or an error
     */
    public Mono<GetAlphaVantageStockResponse> getStock(String symbol) {
        return fetchExchangeRate()
                .flatMap(exchangeRate -> fetchStockQuote(symbol)
                        .map(quote -> convertQuoteToEuro(quote, exchangeRate))
                        .flatMap(quote -> resolveLocalStock(symbol)
                                .map(stock -> buildResponse(quote, stock))
                        )
                )
                .onErrorResume(ex ->
                        Mono.error(
                        new IllegalStateException("Unable to process stock information: " + ex.getMessage())
                ));
    }

    /**
     * Fetches the current USD→EUR exchange rate using the CurrencyCacheService.
     *
     * This method calls a synchronous service to retrieve and cache the exchange rate.
     * Any errors during the retrieval are caught and wrapped in a reactive Mono as IllegalStateException.
     *
     * @return Mono containing the exchange rate value if successful, or an error
     */
    private Mono<BigDecimal> fetchExchangeRate() {
        try {
            BigDecimal rate = currencyCacheService.getUsdToEurRate(apiKey);
            return Mono.just(rate);
        } catch (Exception ex) {
            System.out.println("[AlphaVantageWebClientService]: fetchExchangeRate failed: " + ex.getMessage());
            return Mono.error(new IllegalStateException("Failed to retrieve exchange rate: " + ex.getMessage()));
        }

    }

    /**
     * Queries the Alpha Vantage API for the latest stock quote for the given symbol.
     *
     * The HTTP GET request returns a JSON payload, which is deserialized into an AlphaVantageResponse object.
     * The GlobalQuote portion of the response is validated to ensure key fields are present.
     * Any errors or malformed responses are returned as IllegalStateException.
     *
     * @param symbol the stock ticker to retrieve
     * @return Mono containing the valid GlobalQuote response, or an error
     */
    private Mono<GlobalQuote> fetchStockQuote(String symbol) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "GLOBAL_QUOTE")
                        .queryParam("symbol", symbol)
                        .queryParam("apikey", apiKey)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AlphaVantageResponse.class)
                .flatMap(response -> {
                    GlobalQuote quote = response.getGlobalQuote();
                    if (quote == null || quote.getPrice() == null || quote.getSymbol() == null) {
                        System.out.println("[AlphaVantageWebClientService] fetchStockQuote failed: Invalid or empty stock quote received from API");
                        return Mono.error(new IllegalStateException("Stock quote not found or malformed"));
                    }
                    return Mono.just(quote);
                })
                .onErrorResume(ex -> {
                    System.out.println("[AlphaVantageWebClientService] fetchStockQuote failed: " + ex.getMessage());
                    return Mono.error(new IllegalStateException("Failed to fetch stock quote: " + ex.getMessage()));
                });
    }

    /**
     * Converts all monetary fields in the provided stock quote from USD to EUR
     * using the given exchange rate.
     *
     * Only non-null values are multiplied; null values are safely defaulted to zero.
     *
     * @param quote the original stock quote in USD
     * @param rate the USD→EUR exchange rate
     * @return GlobalQuote object with price fields expressed in EUR
     */
    private GlobalQuote convertQuoteToEuro(GlobalQuote quote, BigDecimal rate) {
        return GlobalQuote.builder()
                .symbol(quote.getSymbol())
                .price(quote.getPrice().multiply(rate))
                .changePercent(quote.getChangePercent())
                .low(quote.getLow().multiply(rate))
                .open(quote.getOpen().multiply(rate))
                .high(quote.getHigh().multiply(rate))
                .previousClose(quote.getPreviousClose().multiply(rate))
                .change(quote.getChange().multiply(rate))
                .volume(quote.getVolume())
                .latestTradingDay(quote.getLatestTradingDay())
                .build();
    }

    /**
     * Resolves a local stock entry based on the given symbol.
     *
     * If the stock exists in the database, it is retrieved.
     * If it does not exist, a new record is created and persisted.
     * All operations are wrapped in a reactive Mono.
     *
     * @param symbol the stock ticker symbol
     * @return Mono containing the resolved or newly created Stock entity
     */
    private Mono<Stock> resolveLocalStock(String symbol) {
        try {
            Stock stock = stockService.stockIsSaved(symbol)
                    ? stockService.findByStockName(symbol)
                    : stockService.save(Stock.builder().stockName(symbol).build());
            return Mono.just(stock);
        } catch (Exception ex) {
            System.out.println("[AlphaVantageWebClientService] resolveLocalStock failed: " + ex.getMessage());
            return Mono.error(new IllegalStateException("Failed to access local stock data: " + ex.getMessage()));
        }
    }

    /**
     * Constructs a unified response DTO from the given GlobalQuote and Stock entity.
     *
     * The resulting object contains metadata from the database and market price data
     * obtained from the external API.
     *
     * @param quote the market data quote in EUR
     * @param stock the resolved database record for the given symbol
     * @return structured response containing all relevant fields
     */
    private GetAlphaVantageStockResponse buildResponse(GlobalQuote quote, Stock stock) {
        return GetAlphaVantageStockResponse.builder()
                .stockId(stock.getIdStock())
                .symbol(quote.getSymbol())
                .price(quote.getPrice())
                .changePercent(quote.getChangePercent())
                .build();
    }

}
