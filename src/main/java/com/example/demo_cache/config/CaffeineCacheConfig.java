package com.example.demo_cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
public class CaffeineCacheConfig {

  private static final Logger LOG = LoggerFactory.getLogger(CaffeineCacheConfig.class);


  @Bean
  public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager("employees");
    cacheManager.setAllowNullValues(false);
    cacheManager.setCaffeine(caffeineCacheBuilder());
    return cacheManager;
  }

  Caffeine<Object, Object> caffeineCacheBuilder() {
    return Caffeine.newBuilder()
        .initialCapacity(100)
        .maximumSize(500)
        //.expireAfterAccess(10, TimeUnit.SECONDS)
        .weakKeys()
            .removalListener(new CustomRemovalListener())
        .recordStats();
  }

  class CustomRemovalListener implements RemovalListener<Object, Object> {
    @Override
    public void onRemoval(Object key, Object value, RemovalCause cause) {
      LOG.info("Key :: " + key + "Cause :: " +  cause.toString() + "Eviction :: " + cause.wasEvicted());
    }
  }
}
