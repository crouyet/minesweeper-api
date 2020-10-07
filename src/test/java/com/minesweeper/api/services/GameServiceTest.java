package com.minesweeper.api.services;

import com.minesweeper.api.model.Cell;
import com.minesweeper.api.model.GameInfo;
import com.minesweeper.api.model.GameStatus;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import static com.minesweeper.api.model.CellState.DISCOVER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    private static Integer INIT_COLS = 3;
    private static Integer INIT_ROWS = 4;
    private static Integer INIT_MINES = 5;

    @Test
    void createGame_ok() {
        GameInfo newGame = this.gameService.create(INIT_COLS, INIT_ROWS, INIT_MINES);

        assertNotNull(newGame);
        assertEquals(INIT_COLS, newGame.getCols());

    }
    @Test
    void interactCell_ok() {

        Integer POS_X = 1;
        Integer POS_Y = 0;

        GameInfo game =  new GameInfo("id", GameStatus.NEW, INIT_COLS, INIT_ROWS, INIT_MINES,  new ArrayList<>());

        for (int i = 0; i < game.getCols(); i++) {
            for (int j = 0; j < game.getRows(); j++) {
                game.getBoard().add(new Cell(i, j));
            }
        }

        game.getBoard().get(0).setMine(true);

        GameInfo newGame = this.gameService.makeMove(DISCOVER, game, POS_X, POS_Y);

        assertNotNull(newGame);

    }

}