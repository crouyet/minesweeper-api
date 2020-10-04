package com.minesweeper.api.services;

import com.minesweeper.api.model.GameInfo;
import org.springframework.stereotype.Service;

import static com.minesweeper.api.model.GameStatus.*;

@Service
public class GameService {

    public GameInfo createGame(Integer rows, Integer cols, Integer mines){
        GameInfo newGame = GameInfo.builder()
                .status(NEW)
                .rows(rows)
                .cols(cols)
                .mines(mines)
                .build();

        newGame.start();

        return newGame;
    }
}
