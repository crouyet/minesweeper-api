package com.minesweeper.api.services;

import com.minesweeper.api.exceptions.InvalidGameStatusException;
import com.minesweeper.api.model.CellState;
import com.minesweeper.api.model.GameInfo;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.minesweeper.api.model.CellState.DISCOVER;
import static com.minesweeper.api.model.GameStatus.*;

@Service
public class GameService {

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

        return newGame;
    }

    public GameInfo makeMove(CellState action, GameInfo game, Integer posX, Integer posY){
        if (NEW.equals(game.getStatus())) {
            //TODO: arrancar contador
            game.setStatus(PLAYING);
        } else if (!PLAYING.equals(game.getStatus())){
            throw new InvalidGameStatusException(String.format("The Game is %s, can't update it.", game.getStatus()));
        }

        if(DISCOVER.equals(action)){
            game.revealCell(posX, posY);
        } else {
            game.changeCellState(action, posX, posY);
        }
        
        return game;
    }

    public GameInfo pause(String gameId) {
        //TODO: frenar contador
        return null;
    }
}
