package com.minesweeper.api.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

import static com.minesweeper.api.model.CellState.*;

@Data
public class Cell implements Serializable {

    private static final long serialVersionUID = 1L;

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


    public boolean isMine() {
        return this.mine;
    }

    public boolean isDiscovered() {
        return DISCOVERED.equals(this.getState());
    }

    public boolean withFlag() {
        return FLAG.equals(this.getState());
    }

    public void reveal(List<Cell> board) {
        this.setState(DISCOVERED);
        //TODO
        // List<Cell> adjacents = board.stream()


    }


}
