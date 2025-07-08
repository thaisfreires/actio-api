package com.actio.actio_api.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        System.out.println("##### ###### Fetching usdToEurRate");
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("usdToEurRate");
        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(Duration.ofDays(1))
                        .maximumSize(1)
        );
        return cacheManager;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}