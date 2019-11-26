package com.example.demo_cache.config;

import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SimpleCacheConfig {

  private static final Logger LOG = LoggerFactory.getLogger(SimpleCacheConfig.class);

  @Bean
  public CacheManager cacheManager() {
    List<String> cacheNames = new ArrayList<>();
    cacheNames.add("employees");
    ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
    cacheManager.setCacheNames(cacheNames);
    return cacheManager;
  }

  class CustomRemovalListener implements RemovalListener<Object, Object> {
    @Override
    public void onRemoval(Object key, Object value, RemovalCause cause) {
      LOG.info(
          "Key :: " + key + "Cause :: " + cause.toString() + "Eviction :: " + cause.wasEvicted());
    }
  }
}
