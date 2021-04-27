package com.betbull.player_market.service;

import com.betbull.player_market.business.TransferCalculator;
import com.betbull.player_market.exception.InvalidInputRequestParamException;
import com.betbull.player_market.modal.ContractFee;
import com.betbull.player_market.modal.Player;
import com.betbull.player_market.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Component
public class PlayerService {

    @Autowired
    PlayerRepository repository;
    @Autowired
    TransferCalculator calculator;
    @Autowired
    TeamService teamService;

    /**
     * Register a player and save to DB
     *
     * @param player object with details
     * @return Registerd player details
     */
    public Player registerPlayer(Player player) {
        if(ObjectUtils.isEmpty(player)){
            throw new InvalidInputRequestParamException("Player object cannot be null. Please provide valid Input");
        }
        log.info("register player in repository");
        repository.save(player);
        return player;
    }

    /**
     * find player by unique player id
     *
     * @param id of the player
     * @return Registerd player details
     */
    public Player findPlayerById(Long id) {
        log.info("find player by id");
        try {
            if (id <= 0) {
                log.error("ID cannot be null");
                throw new InvalidInputRequestParamException("Id should be a valid id");
            }
            return repository.findById(id).get();
        } catch (Exception ex) {
            log.error("cannot find player by the provided id");
            throw new InvalidInputRequestParamException("The requested ID is not present");
        }
    }

    /**
     * find All player
     *
     * @return Registerd player details os List
     */
    public List<Player> findAllPlayer() {
        log.info("Find all player in repository");
        return repository.findAll();
    }

    /**
     * find player by unique player name
     *
     * @param name of the player
     * @return Registerd player details
     */
    public Player findPlayerByName(String name) {
        log.info("Find player by player name");
        try {
            if (name == null) {
                log.error("Player name cannot be null");
                throw new InvalidInputRequestParamException("Player name cannot be null or empty");
            }
            Player p = repository.findPlayerByPlayername(name);
            if (!ObjectUtils.isEmpty(p))
                repository.save(p);

            return p;
        } catch (Exception ex) {
            log.error("Player do not exist in the repository");
            throw new InvalidInputRequestParamException("The requested name is not present");
        }
    }

    /**
     * delete player by unique player name
     *
     * @param name of the player
     * @return boolean if player is deleted
     */
    public boolean deletePlayer(String name) {
        log.info("Delete playe by name{}",name);
        Player player = findPlayerByName(name);
        if (player != null) {
            repository.delete(player);
            return true;
        }
        return false;
    }

    /**
     * Get contract Fee details for a Particular player
     *
     * @param player Name
     * @return Contrect fee details
     */
    public ContractFee getPlayercontractFee(String player) {
        log.info("calculate contract fee for the player");
        Player p = findPlayerByName(player);
        if (p == null || ObjectUtils.isEmpty(p)) {
            log.error("Player cannot be found to calculate contract fee");
            throw new InvalidInputRequestParamException("Player cannot be null");
        }
        return calculator.calculatePlayerContractFee(p, teamService.findTeamByName(p.getTeam()));
    }
}