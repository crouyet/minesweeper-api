package com.minesweeper.api.services;

import com.minesweeper.api.exceptions.InvalidGameException;
import com.minesweeper.api.exceptions.InvalidGameStatusException;
import com.minesweeper.api.model.CellState;
import com.minesweeper.api.model.GameInfo;
import com.minesweeper.api.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.minesweeper.api.model.CellState.DISCOVER;
import static com.minesweeper.api.model.GameStatus.*;

@Service
public class GameService {

    private static final String GAME_NOT_FOUND = "Couldn't find game [%s]";
    private static final String GAME_FINISHED = "The Game is %s, can't update it.";

    @Autowired
    private GameRepository gameRepository;

    public GameInfo create(Integer cols, Integer rows, Integer mines){
        GameInfo newGame = GameInfo.builder()
                .id(UUID.randomUUID().toString())
                .status(NEW)
                .cols(cols)
                .rows(rows)
                .mines(mines)
                .build();

        newGame.createBoard();
        newGame.addMines();

        gameRepository.save(newGame);

        return newGame;
    }

    public GameInfo pause(String gameId) {
        GameInfo game = gameRepository
                .findById(gameId)
                .map( gameInfo -> {
                    gameInfo.setPauseTime(Instant.now());
                    gameInfo.setStatus(PAUSED);
                    return gameInfo;
                })
                .orElseThrow( () -> new InvalidGameException(String.format(GAME_NOT_FOUND, gameId)));

        gameRepository.update(game);

        return game;
    }

    public GameInfo makeMove(CellState action, String gameId, Integer posX, Integer posY){

        GameInfo game = gameRepository
                .findById(gameId)
                .map( gameInfo -> this.move(action, posX, posY, gameInfo))
                .orElseThrow( () -> new InvalidGameException(String.format(GAME_NOT_FOUND, gameId)));

        gameRepository.update(game);

        return game;
    }

    private GameInfo move(CellState action, Integer posX, Integer posY, GameInfo gameInfo) {
        switch (gameInfo.getStatus()){
            case WON: case OVER:
                throw new InvalidGameStatusException(String.format(GAME_FINISHED, gameInfo.getStatus()));

            case NEW: case PAUSED:
                this.start(gameInfo);

            case PLAYING:
                this.play(action, posX, posY, gameInfo);
        }

        return gameInfo;
    }

    private void start(GameInfo game) {
        Duration duration = Duration.ZERO;

        if (Objects.nonNull(game.getPauseTime())) {
            duration = Duration.between( game.getStartTime(), game.getPauseTime());
        }

        game.setStartTime(Instant.now().plus(duration));
        game.setStatus(PLAYING);
    }

    private void play(CellState move, Integer posX, Integer posY, GameInfo game) {
        if(DISCOVER.equals(move)){
            game.revealCell(posX, posY);
        } else {
            game.changeCellState(move, posX, posY);
        }
    }
}
