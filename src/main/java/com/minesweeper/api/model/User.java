package com.minesweeper.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private Map<String, GameInfo> games;

    public User(String username) {
        this.username = username;
        this.games = new HashMap<>();
    }
}

