package com.xjob.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.time.Duration;

//@EnableSwagger2
@Configuration
@EnableCaching
public class AppConfiguration {

//	@Bean
//	public Docket api() {
//		 return new Docket(DocumentationType.SWAGGER_2)
//		          .select()
//		          .apis(RequestHandlerSelectors.any())
//		          .paths(PathSelectors.any())
//		          .build();
//	}

    @Autowired
    private Environment env;

    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        String host = env.getProperty("spring.redis.host") == null ? "localhost" : env.getProperty("spring.redis.host");
        String portStr = env.getProperty("spring.redis.port");
        int port = portStr == null ? 6379 : Integer.parseInt(portStr);
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .disableKeyPrefix();

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(cacheConfiguration)
                .build();
    }

}
