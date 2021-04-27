package com.betbull.player_market.controller;

import com.betbull.player_market.modal.ContractFee;
import com.betbull.player_market.modal.Player;
import com.betbull.player_market.service.PlayerService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    PlayerService service;

    @Test
    void test_registerPlayer() throws Exception {
        Player player = new Player(1L, "AA", "ADAM", "A", 25, "Dare", 0, "abc@i23.com", 34);

        given(service.registerPlayer(player)).willReturn(player);

        mvc.perform(post("/player/register")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(player)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("playername", is("AA")));
        verify(service, VerificationModeFactory.times(1)).registerPlayer(player);
        reset(service);

    }

    @Test
    void test_getPlayerDetailsbyName() throws Exception {
        Player player = new Player(1L, "AA", "ADAM", "A", 25, "Dare", 0, "abc@i23.com", 34);

        given(service.findPlayerByName(anyString())).willReturn(player);

        mvc.perform(get("/player/name/AA")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("playername", is(player.getPlayername())));
        verify(service, VerificationModeFactory.times(1)).findPlayerByName("AA");
        reset(service);
    }

    @Test
    void testGetAllPlayerDetails() throws Exception {
        Player player1 = new Player(1L, "AA", "ADAM", "A", 25, "Dare", 0, "abc@i23.com", 34);
        Player player2 = new Player(2L, "BB", "BDAM", "B", 25, "Bare", 0, "xyz@i23.com", 24);

        List<Player> list = new ArrayList<>();
        list.add(player1);
        list.add(player2);

        given(service.findAllPlayer()).willReturn(list);

        mvc.perform(get("/player/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].playername", is(player2.getPlayername())));
        verify(service, VerificationModeFactory.times(1)).findAllPlayer();
        reset(service);
    }

    @Test
    void testGetPlayerDetailsbyID() throws Exception {
        Player player = new Player(1L, "AA", "ADAM", "A", 25, "Dare", 0, "abc@i23.com", 34);

        given(service.findPlayerById(anyLong())).willReturn(player);

        mvc.perform(get("/player/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("playername", is(player.getPlayername())));
        verify(service, VerificationModeFactory.times(1)).findPlayerById(1L);
        reset(service);
    }

    @Test
    void test_deletePlayer() throws Exception {
        Player player = new Player(1L, "AA", "ADAM", "A", 25, "Dare", 0, "abc@i23.com", 34);

        given(service.deletePlayer(anyString())).willReturn(true);
        mvc.perform(delete("/player/AA")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service, VerificationModeFactory.times(1)).deletePlayer("AA");
        reset(service);
    }

    @Test
    void test_getPlayerContractFee() throws Exception {
        ContractFee fee = new ContractFee("AA", new BigDecimal(100000), new BigDecimal(1000), "EUR");
        given(service.getPlayercontractFee(anyString())).willReturn(fee);

        mvc.perform(get("/player/contract/AA")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("currency", is(fee.getCurrency())));
        ;
        verify(service, VerificationModeFactory.times(1)).getPlayercontractFee("AA");
        reset(service);
    }

    private static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}