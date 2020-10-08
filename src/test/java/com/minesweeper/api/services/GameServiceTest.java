package com.minesweeper.api.services;

import com.minesweeper.api.model.GameInfo;
import com.minesweeper.api.model.GameStatus;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static com.minesweeper.api.model.CellState.DISCOVER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private UserService userService;

    private static Integer INIT_COLS = 3;
    private static Integer INIT_ROWS = 4;
    private static Integer INIT_MINES = 5;

    @Test
    void create_ok() {
        GameInfo newGame = this.gameService.create(INIT_COLS, INIT_ROWS, INIT_MINES);

        assertNotNull(newGame);
        assertEquals(INIT_COLS, newGame.getCols());

    }
    @Test
    void makeMove_ok() {

        Integer POS_X = 1;
        Integer POS_Y = 0;

        GameInfo game =  new GameInfo("id", "UNKNOWN", null, null, GameStatus.NEW, INIT_COLS, INIT_ROWS, INIT_MINES,  new ArrayList<>());
        game.createBoard();
        game.getBoard().get(0).setMine(true);

        when(userService.getGame("id")).thenReturn(Optional.of(game));

        GameInfo newGame = this.gameService.makeMove(DISCOVER, "id", POS_X, POS_Y);

        assertNotNull(newGame);
        assertEquals(GameStatus.WON, newGame.getStatus());

    }

}