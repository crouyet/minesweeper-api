package com.minesweeper.api.services;

import com.minesweeper.api.config.RSD;
import com.minesweeper.api.exceptions.InvalidUserException;
import com.minesweeper.api.model.GameInfo;
import com.minesweeper.api.model.User;
import com.minesweeper.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.minesweeper.api.config.RSD.USERNAME;

@Service
public class UserService {

    public static final String UNKNOWN = "UNKNOWN";
    private static final String INVALID_NAME = "Invalid username [%s]";

    @Autowired
    private UserRepository userRepository;


    public Optional<User> get(String username){
        return userRepository.findById(username);
    }

    public void delete(String username) {
        userRepository.delete(username);
    }

    public void create(String username){
        if (userRepository.findById(username).isPresent()){
            throw new InvalidUserException(String.format(INVALID_NAME, username));
        }

        userRepository.save(new User(getUsername()));
    }

    public void saveNewGame(GameInfo newGame) {

        User user = userRepository.findById(getUsername())
                .orElseGet(() -> userRepository.findById(UNKNOWN)
                        .orElseGet(() -> {
                            User u = new User(UNKNOWN);
                            userRepository.save(u);
                            return u;
                        })
                );

        user.getGames().put(newGame.getId(), newGame);

        userRepository.save(user);
    }

    public Optional<GameInfo> getGame(String gameId){
        return userRepository.findById(getUsername()).map(user -> user.getGames().get(gameId));
    }

    public void updateGame(GameInfo game){

        User user = userRepository.findById(getUsername())
                .map(u -> {
                    u.getGames().put(game.getId(), game);
                    return u;
                }).orElseThrow(() -> new RuntimeException("invalid username"));

        userRepository.save(user);
    }

    public void deleteGame(String gameId){

        User user = userRepository.findById(getUsername())
                .map(u -> {
                    u.getGames().remove(gameId);
                    return u;
                }).orElseThrow(() -> new RuntimeException("invalid username"));

        userRepository.save(user);
    }

    private String getUsername(){
        return RSD.get().get(USERNAME);
    }
}
