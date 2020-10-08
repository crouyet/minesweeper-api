package com.minesweeper.api.repository;

import com.minesweeper.api.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository implements  IRepository<User> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    private static final String USER_KEY = "USER";

    private HashOperations hashOperations;

    public UserRepository(RedisTemplate<String, User> redisTemplate) {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(User user) {
        LOGGER.info("Saving user [{}]", user);
        hashOperations.put(USER_KEY, user.getUsername(), user);
    }
    @Override
    public Map<String,User> findAll() {
        LOGGER.info("Retrieving users");
        return hashOperations.entries(USER_KEY);
    }
    @Override
    public Optional<User> findById(String id) {
        LOGGER.info("Retrieving user [{}]", id);
        try{
            return  Optional.of((User) hashOperations.get(USER_KEY,id));
        } catch (Exception e){
            LOGGER.warn("User not  found [{}]", id);
            return Optional.empty();
        }
    }
    @Override
    public void update(User user) {
        LOGGER.info("Updatinng user [{}]", user.getUsername());
        save(user);
    }
    @Override
    public void delete(String id) {
        LOGGER.info("Deleting user [{}]", id);
        hashOperations.delete(USER_KEY,id);
    }
}
