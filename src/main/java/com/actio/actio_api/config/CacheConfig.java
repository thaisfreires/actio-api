package com.actio.actio_api.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

/**
 * Configuration class responsible for setting up application-level caching using Caffeine,
 * and registering a shared WebClient instance for API communication.
 *
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Defines the cache manager that controls caching behavior for the application.
     *
     * The cache named "usdToEurRate" is configured to:
     * - Expire entries 1 day after they are written to the cache.
     * - Hold a maximum of 1 entry at a time (since only one exchange rate is cached).
     *
     * A log is printed during initialization to confirm cache configuration.
     *
     * @return an instance of CaffeineCacheManager configured for the exchange rate cache
     */
    @Bean
    public CacheManager cacheManager() {
        System.out.println("[CacheConfig] Fetching usdToEurRate");
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("usdToEurRate");
        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(Duration.ofDays(1))
                        .maximumSize(1)
        );
        return cacheManager;
    }

    /**
     * Registers a WebClient bean to be used across the application for making HTTP requests.
     *
     * The client is generic and does not include a base URL, allowing flexible reuse in different services.
     *
     * @return a default WebClient builder instance
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}