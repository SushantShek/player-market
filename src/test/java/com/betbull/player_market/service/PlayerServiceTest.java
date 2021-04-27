package com.betbull.player_market.service;

import com.betbull.player_market.business.TransferCalculator;
import com.betbull.player_market.exception.InvalidInputRequestParamException;
import com.betbull.player_market.modal.ContractFee;
import com.betbull.player_market.modal.Player;
import com.betbull.player_market.modal.TeamEntity;
import com.betbull.player_market.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @InjectMocks
    private PlayerService service;

    @Mock
    PlayerRepository repository;
    @Mock
    TransferCalculator calculator;
    @Mock
    TeamService teamService;

    @Test
    void registerPlayer() {
        Player player = new Player(1L, "AA", "ADAM", "A", 25, "Dare", 0, "abc@i23.com", 34);
        when(repository.save(any(Player.class))).thenReturn(player);
        Player response = service.registerPlayer(player);
        assertNotNull(response);
        assertEquals("AA",response.getPlayername());
    }

    @Test
    void registerPlayer_null_Args(){
        assertThrows(InvalidInputRequestParamException.class, () -> service.registerPlayer(null));
    }

    @Test
    void findPlayerById() {
        Player player = new Player(1L, "AA", "ADAM", "A", 25, "Dare", 0, "abc@i23.com", 34);
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(player));
        Player response = service.findPlayerById(1L);
        assertNotNull(response);
        assertEquals("AA",response.getPlayername());
    }

    @Test
    void findAllPlayer() {
        Player player1 = new Player(1L, "AA", "ADAM", "A", 25, "Dare", 0, "abc@i23.com", 34);
        Player player2 = new Player(2L, "BB", "BDAM", "B", 25, "Bare", 0, "xyz@i23.com", 24);
        List<Player> list = new ArrayList<>();
        list.add(player1);
        list.add(player2);
        when(repository.findAll()).thenReturn(list);
        List<Player> response = service.findAllPlayer();
        assertNotNull(response);
        assertEquals("AA",response.get(0).getPlayername());
        assertTrue(response.size() ==2);
    }

    @Test
    void findPlayerByName() {
        Player player = new Player(1L, "AA", "ADAM", "A", 25, "Dare", 0, "abc@i23.com", 34);
        when(repository.findPlayerByPlayername("AA")).thenReturn(player);
        Player response = service.findPlayerByName("AA");
        assertNotNull(response);
        assertEquals("AA",response.getPlayername());
    }
    @Test
    void findPlayerByName_null_Args(){
        assertThrows(InvalidInputRequestParamException.class, () -> service.findPlayerByName(null));
    }

    @Test
    void getPlayerContractFee() {
        Player player = new Player(1L, "AA", "ADAM", "A", 25, "Dare", 0, "abc@i23.com", 34);
        TeamEntity team = new TeamEntity(1L, "Angel", "Spain", new BigDecimal(100), "EUR");
        ContractFee fee = new ContractFee("AA", new BigDecimal(100000), new BigDecimal(1000), "EUR");

        given(service.findPlayerByName(anyString())).willReturn(player);
        given(repository.findPlayerByPlayername("AA")).willReturn(player);
        when(calculator.calculatePlayerContractFee(player,team)).thenReturn(fee);
        when(teamService.findTeamByName(anyString())).thenReturn(team);

        ContractFee response = service.getPlayercontractFee("AA");
        assertNotNull(response);
        assertEquals("EUR",response.getCurrency());
    }

    @Test
    void getPlayerContractFee_Empty_Player_Exception() {

        assertThrows(InvalidInputRequestParamException.class, () -> service.getPlayercontractFee(null));
    }
}