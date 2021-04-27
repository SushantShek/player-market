package com.betbull.player_market.controller;

import com.betbull.player_market.exception.InvalidInputRequestParamException;
import com.betbull.player_market.modal.Player;
import com.betbull.player_market.modal.TeamChart;
import com.betbull.player_market.modal.TeamEntity;
import com.betbull.player_market.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    TeamService service;

    /**
     * Post mapping to register a team
     *
     * @param teamEntity details of a team
     * @return Team entity with details of registered team
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<TeamEntity> registerTeam(final @Validated @RequestBody TeamEntity teamEntity) {
        log.info("registerTeam ......");
        return new ResponseEntity<>(service.registerTeam(teamEntity), HttpStatus.OK);
    }

    /**
     * Get mapping details by name of a team
     *
     * @param name of the team
     * @return Team entity with details of registered team
     */
    @GetMapping(value = "/name/{name}")
    @ResponseBody
    public ResponseEntity<TeamEntity> getTeamByName(@PathVariable String name) {
        log.info("getTeamByName ......");
        TeamEntity teamEntity = service.findTeamByName(name);
        return new ResponseEntity<>(teamEntity, teamEntity != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Get mapping details by id of a team
     *
     * @param id of the team
     * @return Team entity with details of registered team
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<TeamEntity> getTeamById(@PathVariable Long id) {
        log.info("getTeamById ......");
        TeamEntity teamEntity = service.findTeamById(id);
        return new ResponseEntity<>(teamEntity, teamEntity != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Delete mapping to remove team by name
     *
     * @param name of the team
     * @return Ok or not found if the team is not present
     */
    @DeleteMapping(value = "/{name}")
    @ResponseBody
    public ResponseEntity<Void> deleteTeam(@PathVariable String name) {
        log.info("deleteTeam ......");
        return new ResponseEntity<>(service.deleteTeam(name) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * Post mapping to get details of all players in the list of players
     *
     * @param teamChart object which contains list of players
     * @return list of players with there details
     */
    @PostMapping("/build")
    @ResponseBody
    public ResponseEntity<List<Player>> registerTeamChart(final @Validated @RequestBody TeamChart teamChart) {
        log.info("registerTeamChart ......");
        if(teamChart.getPlayers().isEmpty()){
            throw new InvalidInputRequestParamException("Provide valid list of Player Names");
        }
        return new ResponseEntity<>(service.buildTeam(teamChart), HttpStatus.OK);
    }
}
