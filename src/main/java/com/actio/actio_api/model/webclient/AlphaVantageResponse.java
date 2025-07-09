package com.actio.actio_api.model.webclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Wrapper class for parsing Alpha Vantage's response for the GLOBAL_QUOTE function.
 *
 * The Alpha Vantage API returns stock quote data under the key "Global Quote".
 * This class maps that portion of the response to a structured {@link GlobalQuote} object.
 *
 * Used primarily in the service layer to deserialize JSON and provide access to stock quote data.
 */
@Data
public class AlphaVantageResponse {

    @JsonProperty("Global Quote")
    private GlobalQuote globalQuote;

    public GlobalQuote getGlobalQuote() {
        return globalQuote;
    }

}
