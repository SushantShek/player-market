package com.betbull.player_market.service;

import com.betbull.player_market.exception.InvalidInputRequestParamException;
import com.betbull.player_market.modal.Player;
import com.betbull.player_market.modal.TeamChart;
import com.betbull.player_market.modal.TeamEntity;
import com.betbull.player_market.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @InjectMocks
    private TeamService service;
    @Mock
    TeamRepository repository;
    @Mock
    PlayerService playerService;

    @Test
    void registerTeam() {
        TeamEntity team = new TeamEntity(1L, "Angel", "Spain", new BigDecimal(100), "EUR");
        when(repository.save(any(TeamEntity.class))).thenReturn(team);
        TeamEntity response = service.registerTeam(team);
        assertNotNull(response);
        assertEquals("Angel", response.getTeamname());
        assertEquals("Spain", response.getLocation());
    }
    @Test
    void registerTeam_null_Args(){
        assertThrows(InvalidInputRequestParamException.class, () -> service.registerTeam(null));
    }


    @Test
    void findTeamByName() {
        TeamEntity team = new TeamEntity(1L, "Angel", "Spain", new BigDecimal(100), "EUR");
        when(repository.findTeamEntityByTeamname(anyString())).thenReturn(team);
        TeamEntity response = service.findTeamByName("Angel");
        assertNotNull(response);
        assertEquals("Angel", response.getTeamname());
        assertEquals("Spain", response.getLocation());
    }

    @Test
    void findTeamByName_null_Args(){
        assertThrows(InvalidInputRequestParamException.class, () -> service.findTeamByName(null));
    }

    @Test
    void findTeamById() {
        TeamEntity team = new TeamEntity(1L, "Angel", "Spain", new BigDecimal(100), "EUR");
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(team));
        TeamEntity response = service.findTeamById(1L);
        assertNotNull(response);
        assertEquals("Angel", response.getTeamname());
        assertEquals("Spain", response.getLocation());
    }

    @Test
    void buildTeam() {
        Player player1 = new Player(1L, "AA", "ADAM", "A", 25, "Dare", 0, "abc@i23.com", 34);
        Player player2 = new Player(2L, "BB", "BDAM", "B", 25, "Bare", 0, "xyz@i23.com", 24);
        List<Player> list = new ArrayList<>();
        list.add(player1);
        list.add(player2);

        TeamChart chart = new TeamChart(Arrays.asList(new String[]{"AA", "BB"}));

        when(playerService.findAllPlayer()).thenReturn(list);
       List<Player> response =  service.buildTeam(chart);
       assertNotNull(response);
       assertTrue(response.size() == 2);
    }

    @Test
    void buildTeam_null_Args(){
        assertThrows(InvalidInputRequestParamException.class, () -> service.buildTeam(null));
    }
}