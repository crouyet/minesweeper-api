package com.minesweeper.api.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.stream.Collectors;

import static com.minesweeper.api.model.CellState.DISCOVER;
import static org.junit.jupiter.api.Assertions.*;

class GameInfoTest {

    private static Integer INIT_COLS = 3;
    private static Integer INIT_ROWS = 4;
    private static Integer INIT_MINES = 1;

    @Test
    void createBoard_ok() {

       GameInfo newGame =  new GameInfo("id",null, null, GameStatus.NEW, INIT_COLS, INIT_ROWS, INIT_MINES, null );

       newGame.createBoard();
       newGame.addMines();

        assertNotNull(newGame);

        assertEquals(INIT_COLS * INIT_ROWS, newGame.getBoard().size());

        assertEquals(INIT_MINES, newGame.getBoard()
                .stream()
                .filter(Cell::getMine)
                .collect(Collectors.toList())
                .size());
    }

    @Test
    void revealCell_ok() {

        Integer POS_X = 1;
        Integer POS_Y = 0;

        GameInfo game =  new GameInfo("id", null, null, GameStatus.NEW, INIT_COLS, INIT_ROWS, INIT_MINES,  null);

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

        GameInfo game =  new GameInfo("id", Instant.now(), null, GameStatus.PLAYING, INIT_COLS, INIT_ROWS, INIT_MINES,  null);

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

        GameInfo game =  new GameInfo("id", null, null, GameStatus.NEW, INIT_COLS, INIT_ROWS, INIT_MINES,  null);

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