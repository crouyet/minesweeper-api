package com.minesweeper.api.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.stream.Collectors;

import static com.minesweeper.api.model.CellState.DISCOVER;
import static org.junit.jupiter.api.Assertions.*;

class GameInfoTest {

    private static Integer INIT_COLS = 3;
    private static Integer INIT_ROWS = 4;
    private static Integer INIT_MINES = 1;
    private GameInfo game;

    @BeforeEach
    void setUp() {
        game =  new GameInfo("id", null, null, GameStatus.NEW, INIT_COLS, INIT_ROWS, INIT_MINES, null );
    }
    @Test
    void createBoard_ok() {

        game.createBoard();
        game.addMines();

        assertNotNull(game);

        assertEquals(INIT_COLS * INIT_ROWS, game.getBoard().size());

        assertEquals(INIT_MINES, game.getBoard()
                .stream()
                .filter(Cell::getMine)
                .collect(Collectors.toList())
                .size());
    }

    @Test
    void revealCell_ok() {

        Integer POS_X = 1;
        Integer POS_Y = 0;


        game.createBoard();
        game.getBoard().get(0).setMine(true);
        game.revealCell(POS_X, POS_Y);

        assertNotNull(game);
        assertEquals(GameStatus.WON, game.getStatus());

        assertEquals(INIT_MINES, game.getBoard()
                .stream()
                .filter(cell -> !DISCOVER.equals(cell.getState()))
                .collect(Collectors.toList()).size());

    }

    @Test
    void revealCell_withFlag() {

        Integer POS_X = 0;
        Integer POS_Y = 0;

        game.setStatus(GameStatus.PLAYING);
        game.setStartTime(Instant.now());
        game.createBoard();
        game.getBoard().get(0).setState(CellState.FLAG);
        game.revealCell(POS_X, POS_Y);

        assertNotNull(game);
        assertEquals(0, game.getBoard()
                .stream()
                .filter(cell -> DISCOVER.equals(cell.getState()))
                .collect(Collectors.toList()).size());

    }

    @Test
    void revealCell_isMine() {

        Integer POS_X = 0;
        Integer POS_Y = 0;

        game.createBoard();
        game.getBoard().get(0).setMine(true);
        game.revealCell(POS_X, POS_Y);

        assertNotNull(game);
        assertEquals(GameStatus.OVER, game.getStatus());
        assertEquals(0, game.getBoard()
                .stream()
                .filter(cell -> DISCOVER.equals(cell.getState()))
                .collect(Collectors.toList()).size());

    }
}