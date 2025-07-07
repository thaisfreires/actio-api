package com.actio.actio_api.model.webclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class GlobalQuote {

    @JsonProperty("01. symbol")
    private String symbol;

    @JsonProperty("02. open")
    private Double open;

    @JsonProperty("03. high")
    private Double high;

    @JsonProperty("04. low")
    private Double low;

    @JsonProperty("05. price")
    private Double price;

    @JsonProperty("06. volume")
    private BigInteger volume;

    @JsonProperty("07. latest trading day")
    private String latestTradingDay;

    @JsonProperty("08. previous close")
    private Double previousClose;

    @JsonProperty("09. change")
    private Double change;

    @JsonProperty("10. change percent")
    private String changePercent;
}