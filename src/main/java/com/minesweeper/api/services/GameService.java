package com.minesweeper.api.services;

import com.minesweeper.api.exceptions.InvalidGameStatusException;
import com.minesweeper.api.model.CellState;
import com.minesweeper.api.model.GameInfo;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.minesweeper.api.model.CellState.DISCOVERED;
import static com.minesweeper.api.model.GameStatus.*;

@Service
public class GameService {

    public GameInfo createGame(Integer cols, Integer rows, Integer mines){
        GameInfo newGame = GameInfo.builder()
                .id(UUID.randomUUID().toString())
                .status(NEW)
                .cols(cols)
                .rows(rows)
                .mines(mines)
                .build();

        newGame.createBoard();

        return newGame;
    }

    public GameInfo interactCell(CellState action, GameInfo game, Integer posX, Integer posY){
        if (NEW.equals(game.getStatus())) {
            //TODO: arrancar contador
            game.setStatus(PLAYING);
        } else if (!PLAYING.equals(game.getStatus())){
            throw new InvalidGameStatusException(String.format("The Game is %s, can not update it.", game.getStatus()));
        }

        if(DISCOVERED.equals(action)){
            game.revealCell(posX, posY);
        } else {
            game.changeCellState(action, posX, posY);
        }
        
        return game;
    }
}
