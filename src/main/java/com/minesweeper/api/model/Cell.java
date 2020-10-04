package com.minesweeper.api.model;

import lombok.Data;

import static com.minesweeper.api.model.CellState.*;

@Data
public class Cell {
    Integer posX;
    Integer posY;
    CellState state;
    Boolean mine;

    public Cell(final Integer posX, final Integer posY) {
        this.posX = posX;
        this.posY = posY;
        this.mine = false;
        this.state = CLOSED;
    }
}
