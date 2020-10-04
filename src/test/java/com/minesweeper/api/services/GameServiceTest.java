package com.minesweeper.api.services;

import com.minesweeper.api.model.Cell;
import com.minesweeper.api.model.GameInfo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Test
    void createGame_ok() {

        Integer INIT_ROWS = 4;
        Integer INIT_COLS = 5;
        Integer INIT_MINES = 10;

        GameInfo newGame = this.gameService.createGame(INIT_ROWS, INIT_COLS, INIT_MINES);

        assertNotNull(newGame);

        assertEquals(INIT_COLS * INIT_ROWS, newGame.getBoard().size());

        assertEquals(INIT_MINES, newGame.getBoard()
                .stream()
                .filter(Cell::getMine)
                .collect(Collectors.toList())
                .size());
    }

}