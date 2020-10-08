package com.minesweeper.api.controller;

import com.minesweeper.api.model.CellState;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    private static final String NEW_GAME_ERROR = "Error trying to createBoard new game for user [%s]";
    private static final String NEW_GAME_PARAMS_ERROR = "Mines have to be less than squares available";
    private static final String MAKE_MOVE_ERROR = "Error trying to make a move for [%s]";
    private static final String PAUSE_ERROR = "Error trying to pause game [%s]";

    @Autowired
    GameService gameService;

    @GetMapping("/new")
    public ResponseEntity<GameInfo> startNew(
            //TODO: add session token
            // @CookieValue(value = "session") String session)
            @RequestParam(required = false, value = "user") String user,
            @RequestParam(value = "cols", defaultValue = "5") Integer cols,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "mines", defaultValue = "10") Integer mines){

        if (mines >= cols*rows) {
            return new ResponseEntity(new IllegalArgumentException(NEW_GAME_PARAMS_ERROR), HttpStatus.BAD_REQUEST);
        }

        try {
            GameInfo game = gameService.create(cols, rows, mines);
            return new ResponseEntity<>(game, HttpStatus.CREATED);

        } catch(Exception e){
            LOGGER.error(String.format(NEW_GAME_ERROR, user), e);
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}/{move}")
    public ResponseEntity<GameInfo> makeMove(
            // @CookieValue(value = "session") String session)
            @PathVariable(value = "id") String gameId,
            @PathVariable CellState move,
            @RequestParam(value = "posX") Integer posX,
            @RequestParam(value = "posY") Integer posY){

        try {
            GameInfo game = gameService.makeMove(move, gameId, posX, posY);
            return ResponseEntity.ok(game);

        } catch(IllegalArgumentException e){
            LOGGER.error(e.getMessage(), e);
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);

        } catch(Exception e){
            LOGGER.error(String.format(MAKE_MOVE_ERROR, gameId), e);
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "{id}/pause")
    public ResponseEntity<GameInfo> pauseGame(
            // @CookieValue(value = "session") String session)
            @PathVariable(value = "id") final String gameId) {
        try {
            GameInfo game = gameService.pause(gameId);
            return ResponseEntity.ok(game);

        } catch(IllegalArgumentException e){
            LOGGER.error(e.getMessage(), e);
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);

        } catch (final Exception e) {
            LOGGER.error(String.format(PAUSE_ERROR, gameId), e);
            return new ResponseEntity(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
