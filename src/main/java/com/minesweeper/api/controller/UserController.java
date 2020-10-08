package com.minesweeper.api.controller;

import com.minesweeper.api.config.RSD;
import com.minesweeper.api.model.User;
import com.minesweeper.api.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/{username}")
    public ResponseEntity save(@PathVariable String username){
        try {
            userService.create(username);
            RSD.save(RSD.USERNAME, username);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch(Exception e){
            LOGGER.error("Exception trying to save user [" + username + "]", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity getUser(@PathVariable String username){

        try {
            Optional<User> user = userService.get(username);
            RSD.save(RSD.USERNAME, username);
            return ResponseEntity.ok(user);

        } catch(Exception e){
            LOGGER.error("Exception trying to save user [" + username + "]", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity delete(@PathVariable String username){
        try {
            userService.delete(username);
            RSD.clear();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch(Exception e){
            LOGGER.error("Exception trying to delete user [" + username + "]", e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}