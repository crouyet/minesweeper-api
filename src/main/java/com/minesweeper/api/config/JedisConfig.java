package com.minesweeper.api.config;

import com.minesweeper.api.model.GameInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class JedisConfig {

    @Bean
    @Autowired
    public JedisConnectionFactory redisConnectionFactory(@Value("${redis.host}") String host) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, 6379);
        return  new JedisConnectionFactory(config);
    }

    @Bean
    @Autowired
    public RedisTemplate<String, GameInfo> redisTemplate(JedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, GameInfo> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
