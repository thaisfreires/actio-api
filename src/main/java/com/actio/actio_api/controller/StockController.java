package com.actio.actio_api.controller;

import com.actio.actio_api.model.webclient.GlobalQuote;
import com.actio.actio_api.service.AlphaVantageWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final AlphaVantageWebClientService service;

    @GetMapping("/{symbol}")
    public ResponseEntity<?> getStock(@PathVariable String symbol) {
        return ResponseEntity.ok(service.getStock(symbol).block());
    }

}
