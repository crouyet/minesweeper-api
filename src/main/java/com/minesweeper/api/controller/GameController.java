package com.minesweeper.api.controller;

import com.minesweeper.api.model.GameInfo;
import com.minesweeper.api.services.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/games")
public class GameController {

    @Autowired
    GameService gameService;

    @GetMapping("/new")
    public ResponseEntity startNew(
            //TODO: add session token
            // @CookieValue(value = "session") String session)
            //@RequestParam(required = false, value = "user") String user,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "cols", defaultValue = "5") Integer cols,
            @RequestParam(value = "mines", defaultValue = "10") Integer mines){

        if (mines >= cols*rows) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        GameInfo game = gameService.createGame(rows, cols, mines);

        return new ResponseEntity<>(game, HttpStatus.OK);
    }
}
