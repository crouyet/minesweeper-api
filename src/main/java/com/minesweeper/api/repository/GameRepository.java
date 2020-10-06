package com.minesweeper.api.repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.minesweeper.api.model.GameInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepository implements  IRepository<GameInfo> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameRepository.class);

    private static final String GAME_KEY = "GAME";

    private HashOperations hashOperations;

    public GameRepository(RedisTemplate<String, GameInfo> redisTemplate) {
        hashOperations = redisTemplate.opsForHash();
    }

    public Map<String,GameInfo> findAllByUser() {
        LOGGER.info("Retrieving games");
        return hashOperations.entries(GAME_KEY);
    }
    @Override
    public void save(GameInfo game) {
        LOGGER.info("Saving game [{}]", game);
        hashOperations.put(GAME_KEY, game.getId(), game);
    }
    @Override
    public Map<String,GameInfo> findAll() {
        LOGGER.info("Retrieving games");
        return hashOperations.entries(GAME_KEY);
    }
    @Override
    public Optional<GameInfo> findById(String id) {
        LOGGER.info("Retrieving game [{}]", id);
        return  Optional.ofNullable((GameInfo) hashOperations.get(GAME_KEY,id));
    }
    @Override
    public void update(GameInfo game) {
        LOGGER.info("Updatinng game [{}]", game.getId());
        save(game);
    }
    @Override
    public void delete(String id) {
        LOGGER.info("Deleting game [{}]", id);
        hashOperations.delete(GAME_KEY,id);
    }
}
