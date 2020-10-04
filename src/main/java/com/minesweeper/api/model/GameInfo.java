package com.minesweeper.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class GameInfo {
    private Long id;
    private GameStatus status;
    private Integer rows;
    private Integer cols;
    private Integer mines;
    private List<Cell> board;

    public void start() {
        this.board = new ArrayList<>();

        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getCols(); j++) {
                this.getBoard().add(new Cell(i, j));
            }
        }

        Collections.shuffle(this.getBoard());

        this.getBoard()
                .stream()
                .limit(this.getMines())
                .forEach(cellMine -> cellMine.setMine(true));

    }


}
