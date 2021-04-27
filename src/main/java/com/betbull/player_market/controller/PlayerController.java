package com.betbull.player_market.controller;

import com.betbull.player_market.modal.ContractFee;
import com.betbull.player_market.modal.Player;
import com.betbull.player_market.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    PlayerService service;

    /**
     * Post request to register a player
     * @param player object
     * @return Registerd player details
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Player> registerPlayer(final @Validated @RequestBody Player player) {
        log.info("registerPlayer ....");
        return new ResponseEntity<>(service.registerPlayer(player), HttpStatus.OK);
    }

    /**
     * Get mapping to get player by name
     * @param name of the player
     * @return Registerd player details
     */
    @GetMapping(value = "/name/{name}")
    @ResponseBody
    public ResponseEntity<Player> getPlayerDetailsbyName(@PathVariable String name) {
        log.info("getPlayerDetailsbyName ....");
        Player player = service.findPlayerByName(name);
        return new ResponseEntity<>(player, player != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Get mapping to get  all players
     * @return Registerd List of players
     */
    @GetMapping(value = "/")
    @ResponseBody
    public ResponseEntity<List<Player>> getAllPlayerDetails() {
        log.info("getAllPlayerDetails ....");
        List<Player> players = service.findAllPlayer();
        return new ResponseEntity<>(players, players != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Get mapping to get player by id
     * @param id of the player
     * @return Registerd player details
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Player> getPlayerDetailsbyID(@PathVariable Long id) {
        log.info("getPlayerDetailsbyID ....");
        Player player = service.findPlayerById(id);
        return new ResponseEntity<>(player, player != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Delete mapping to remove player by name
     *
     * @param name of the player
     * @return Ok or not found if the player is not present
     */
    @DeleteMapping(value = "/{name}")
    @ResponseBody
    public ResponseEntity<Void> deletePlayer(@PathVariable String name) {
        log.info("deletePlayer ....");
        return new ResponseEntity<>(service.deletePlayer(name) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Calculate the player contract fee
     * @param name of the player
     * @return Contract Fee with details of the player
     */
    @GetMapping(value = "/contract/{name}")
    @ResponseBody
    public ResponseEntity<ContractFee> getPlayerContractFee(@PathVariable String name) {
        log.info("getPlayerContractFee ....");
        ContractFee fee = service.getPlayercontractFee(name);
        return new ResponseEntity<>(fee, fee != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
