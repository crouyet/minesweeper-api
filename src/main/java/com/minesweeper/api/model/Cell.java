package com.minesweeper.api.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static com.minesweeper.api.model.CellState.*;

@Data
public class Cell implements Serializable {

    private static final long serialVersionUID = 1L;

    Integer posX;
    Integer posY;
    CellState state;
    Boolean mine;

    public Cell(Integer posX, Integer posY) {
        this.posX = posX;
        this.posY = posY;
        this.mine = false;
        this.state = CLOSE;
    }


    public boolean isMine() {
        return this.mine;
    }

    public boolean isDiscovered() {
        return DISCOVER.equals(this.getState());
    }

    public boolean withFlag() {
        return FLAG.equals(this.getState());
    }

    public void reveal(List<Cell> board) {
        this.setState(DISCOVER);

        List<Cell> adjacentCells = getAdjacents(board);

        if (!adjacentCells.isEmpty()
                && adjacentCells.stream().noneMatch(Cell::isMine)) {
            adjacentCells.forEach(otherCell -> otherCell.reveal(board));
        }
    }

    private List<Cell> getAdjacents(List<Cell> board) {
        return board.stream()
                .filter(otherCell -> otherCell.isDiscoverable() && this.isAdjacent(otherCell))
                .collect(Collectors.toList());
    }

    private boolean isDiscoverable() {
        return !this.isDiscovered() && !this.withFlag();
    }

    private boolean isAdjacent(Cell otherCell){
        int distanceX = Math.abs(this.getPosX() - otherCell.getPosX());
        int distanceY = Math.abs(this.getPosY() - otherCell.getPosY());

        return this != otherCell && distanceX <= 1 && distanceY <= 1;
    }
}
