package com.actio.actio_api.model.response;

import com.actio.actio_api.model.webclient.GlobalQuote;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AlphaVantageResponse {

    @JsonProperty("Global Quote")
    private GlobalQuote globalQuote;

    public GlobalQuote getGlobalQuote() {
        return globalQuote;
    }

}
