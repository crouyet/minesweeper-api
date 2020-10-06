package com.minesweeper.api.model;

import com.minesweeper.api.exceptions.InvalidCellException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.minesweeper.api.model.GameStatus.*;

@Data
@AllArgsConstructor
@Builder
public class GameInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private GameStatus status;
    private Integer cols;
    private Integer rows;
    private Integer mines;
    private List<Cell> board;

    public void createBoard() {
        this.board = new ArrayList<>();

        for (int i = 0; i < this.getCols(); i++) {
            for (int j = 0; j < this.getRows(); j++) {
                this.getBoard().add(new Cell(i, j));
            }
        }

        Collections.shuffle(this.getBoard());

        this.getBoard()
                .stream()
                .limit(this.getMines())
                .forEach(cellMine -> cellMine.setMine(true));

    }

    public void changeCellState(CellState newState,  Integer posX, Integer posY) {
        final Cell cellIn = this.findCellIn(posX, posY);
        cellIn.setState(newState);
    }

    public void revealCell(Integer posX, Integer posY) {
        final Cell cellIn = this.findCellIn(posX, posY);

        if (cellIn.withFlag()) {
            return;
        }

        if (cellIn.isMine()) {
            this.setStatus(OVER);
            return;
        }

        cellIn.reveal(this.getBoard());

        if (this.noMinesLeft()) {
            this.status = WON;
        }
    }

    private Cell findCellIn(Integer posX, Integer posY) {
        return this.getBoard()
                .stream()
                .filter(cell -> Objects.equals(posX, cell.getPosX()) && Objects.equals(posY, cell.getPosY()))
                .findFirst()
                .orElseThrow(() -> new InvalidCellException("Cell not found for the given coordinates"));
    }

    private boolean noMinesLeft() {
        return this.getBoard()
                .stream()
                .filter(cell -> !cell.getMine())
                .allMatch(Cell::isDiscovered);
    }

}
