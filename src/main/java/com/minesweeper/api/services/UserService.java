package com.minesweeper.api.services;

import com.minesweeper.api.config.RSD;
import com.minesweeper.api.exceptions.InvalidUserException;
import com.minesweeper.api.model.GameInfo;
import com.minesweeper.api.model.User;
import com.minesweeper.api.repository.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.minesweeper.api.config.RSD.USERNAME;

@Service
public class UserService {

    public static final String UNKNOWN = "UNKNOWN";
    private static final String INVALID_NAME = "Invalid username [%s]";
    private static final String INVALID_USER_GAME = "Invalid username for game [%s]";

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

        User user = getUser().orElseGet(() -> new User(UNKNOWN));

        user.getGames().put(newGame.getId(), newGame);

        userRepository.save(user);
    }

    public Optional<GameInfo> getGame(String gameId){
        return getUser().map(user -> user.getGames().get(gameId));
    }

    public void updateGame(GameInfo game){

        User user = getUser()
                .map(u -> {
                    u.getGames().put(game.getId(), game);
                    return u;
                }).orElseThrow(() -> new InvalidUserException(String.format(INVALID_USER_GAME, game.getId())));

        userRepository.save(user);
    }

    public void deleteGame(String gameId){

        User user = getUser()
                .map(u -> {
                    u.getGames().remove(gameId);
                    return u;
                }).orElseThrow(() -> new InvalidUserException(String.format(INVALID_USER_GAME, gameId)));

        userRepository.save(user);
    }


    private Optional<User> getUser() {
        Optional<User> user;

        if(Strings.isBlank(getUsername())) {
            user = userRepository.findById(UNKNOWN);
        } else {
            user = userRepository.findById(getUsername());
        }

        return user;
    }
    
    private String getUsername(){
        return RSD.get().get(USERNAME);
    }
}
