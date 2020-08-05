package com.delivery.customer.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.concurrent.TimeUnit;


@EnableCaching
@Configuration
public class CacheConfiguration {

    private static final Integer maximumSize = 1000;

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();

        CaffeineCache customersByCity = new CaffeineCache(CacheKeys.CUSTOMERS_BY_CITY, Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .maximumSize(maximumSize)
                .build());

        simpleCacheManager.setCaches(Collections.singletonList(customersByCity));
        return simpleCacheManager;
    }
}
