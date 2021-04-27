package com.betbull.player_market.service;

import com.betbull.player_market.exception.InvalidInputRequestParamException;
import com.betbull.player_market.modal.Player;
import com.betbull.player_market.modal.TeamChart;
import com.betbull.player_market.modal.TeamEntity;
import com.betbull.player_market.repository.TeamRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TeamService {

    @Autowired
    TeamRepository repository;

    @Autowired
    PlayerService playerService;

    /**
     * Register a team with all its details
     *
     * @param teamEntity object with team detail's
     * @return Registered team details
     */
    public TeamEntity registerTeam(TeamEntity teamEntity) {
        if(ObjectUtils.isEmpty(teamEntity)){
            throw new InvalidInputRequestParamException("TeamEntity cannot be null. Please provide valid input");
        }
        log.info("Add team to repository");
        repository.save(teamEntity);
        return teamEntity;
    }

    /**
     * Find the detais of a team by team name
     *
     * @param name of the team
     * @return Registered team details
     */
    public TeamEntity findTeamByName(String name) {
        log.info("Find team from repository by {}",name);
        try {
            if (name == null) {
                log.error("Find team from repository by null name");
                throw new InvalidInputRequestParamException("Team name cannot be null or empty");
            }
            return repository.findTeamEntityByTeamname(name);
        } catch (Exception e) {
            log.error("Exception while finding Team by {}",name);
            throw new InvalidInputRequestParamException("The requested name is not present");
        }
    }

    /**
     * Find the detais of a team by team id
     *
     * @param id of the team
     * @return Registered team details
     */
    public TeamEntity findTeamById(Long id) {
        log.info("Find team from repository by {}",id);
        try {
            if (id <= 0) {
                log.error("Find team from repository by invalid id");
                throw new IllegalArgumentException("Id should be a valid id");
            }
            return repository.findById(id).get();
        } catch (Exception e) {
            log.error("Exception while finding Team by {}",id);
            throw new InvalidInputRequestParamException("The requested ID is not present");
        }
    }

    /**
     * Delete team by team name
     *
     * @param name of the team
     * @return true if deleted successfully
     */
    public boolean deleteTeam(String name) {
        if (name == null) {
            log.error("Exception while deleting Team with null name");
            throw new InvalidInputRequestParamException("Team name cannot be null or empty");
        }
        TeamEntity teamEntity = findTeamByName(name);
        if (teamEntity != null) {
            repository.delete(teamEntity);
            return true;
        }else{
            log.warn("No Team by {}",name);
        }
        return false;
    }

    /**
     * Build a chart of all the players with details
     *
     * @param teamChart list of player to be in team
     * @return list of player with details
     */
    public List<Player> buildTeam(TeamChart teamChart) {
        if (teamChart == null || ObjectUtils.isEmpty(teamChart)) {
            log.error("Team chart cannot be null");
            throw new InvalidInputRequestParamException("Team name cannot be null or empty");
        }
        List<Player> allPlayerList = playerService.findAllPlayer();
        List<Player> playerSelected = allPlayerList
                .stream()
                .filter(p -> teamChart.getPlayers().contains(p.getPlayername()))
                .collect(Collectors.toList());
        return playerSelected;
    }
}
