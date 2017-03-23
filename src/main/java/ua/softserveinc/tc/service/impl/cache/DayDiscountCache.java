package ua.softserveinc.tc.service.impl.cache;

import com.google.common.cache.CacheBuilder;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tat0 on 16.03.2017.
 */
@Configuration
@EnableCaching
public class DayDiscountCache {
  @Bean
  public CacheManager cacheManager() {
    SimpleCacheManager cacheManager = new SimpleCacheManager();
    GuavaCache guavaCache = new GuavaCache("fullDayDiscountList", CacheBuilder.newBuilder()
        .maximumSize(100)
        .expireAfterAccess(3, TimeUnit.HOURS)
        .build());
    cacheManager.setCaches(Arrays.asList(guavaCache));
    return cacheManager;
  }
}
