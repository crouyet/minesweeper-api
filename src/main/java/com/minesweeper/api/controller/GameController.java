package com.minesweeper.api.controller;

import com.minesweeper.api.exceptions.InvalidGameException;
import com.minesweeper.api.model.CellState;
import com.minesweeper.api.model.GameInfo;
import com.minesweeper.api.repository.GameRepository;
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

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    GameService gameService;

    @GetMapping("/new")
    public ResponseEntity startNew(
            //TODO: add session token
            // @CookieValue(value = "session") String session)
            @RequestParam(required = false, value = "user") String user,
            @RequestParam(value = "cols", defaultValue = "5") Integer cols,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "mines", defaultValue = "10") Integer mines){

        if (mines >= cols*rows) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            GameInfo game = gameService.create(cols, rows, mines);
            gameRepository.save(game);
            return ResponseEntity.ok(game);

        } catch(Exception e){
            LOGGER.error(String.format("Error trying to createBoard new game for user [%s]", user), e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}/{action}")
    public ResponseEntity makeMove(
            // @CookieValue(value = "session") String session)
            @PathVariable(value = "id") String gameId,
            @PathVariable CellState action,
            @RequestParam(value = "posX") Integer posX,
            @RequestParam(value = "posY") Integer posY){

        try {
            GameInfo game = gameRepository
                .findById(gameId)
                .map( gameInfo -> gameService.makeMove(action, gameInfo, posX, posY))
                .orElseThrow( () -> new InvalidGameException(String.format("Couldn't find game [%s]", gameId)));

            gameRepository.update(game);
            return ResponseEntity.ok(game);

        } catch(IllegalArgumentException e){
            LOGGER.error(e.getMessage(), e);
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);

        } catch(Exception e){
            LOGGER.error(String.format("Error trying to make a move for [%s]", gameId), e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "{id}/pause")
    public ResponseEntity pauseGame(
            // @CookieValue(value = "session") String session)
            @PathVariable(value = "id") final String gameId) {
        try {
            GameInfo game = gameRepository
                    .findById(gameId)
                    .map( gameInfo -> gameService.pause(gameId))
                    .orElseThrow( () -> new InvalidGameException(String.format("Couldn't find game [%s]", gameId)));

            gameRepository.update(game);
            return ResponseEntity.ok(game);
        } catch(IllegalArgumentException e){
            LOGGER.error(e.getMessage(), e);
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);

        } catch (final Exception e) {
            LOGGER.error(String.format("Error trying to pause game [%s]", gameId), e);
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
