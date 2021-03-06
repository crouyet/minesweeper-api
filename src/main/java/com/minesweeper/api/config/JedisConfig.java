package com.minesweeper.api.config;

import com.minesweeper.api.model.User;
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
    public JedisConnectionFactory redisConnectionFactory(
            @Value("${redis.host}") String host,
            @Value("${redis.port}") Integer port,
            @Value("${redis.pass}") String pass) {

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        config.setPassword(pass);
        return  new JedisConnectionFactory(config);
    }

    @Bean
    @Autowired
    public RedisTemplate<String, User> redisTemplate(JedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, User> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
