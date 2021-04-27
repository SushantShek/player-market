package com.betbull.player_market.controller;

import com.betbull.player_market.modal.Player;
import com.betbull.player_market.modal.TeamChart;
import com.betbull.player_market.modal.TeamEntity;
import com.betbull.player_market.service.TeamService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Arrays;
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
@WebMvcTest(TeamController.class)
class TeamControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    TeamService service;

    @Test
    void test_registerTeam() throws Exception {
        TeamEntity team = new TeamEntity(1L, "Angel", "Spain", new BigDecimal(100), "EUR");

        given(service.registerTeam(team)).willReturn(team);

        mvc.perform(post("/team/register")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(team)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("teamname", is("Angel")));
        verify(service, VerificationModeFactory.times(1)).registerTeam(team);
        reset(service);
    }

    @Test
    void test_getTeamByName() throws Exception {
        TeamEntity team = new TeamEntity(1L, "Angel", "Spain", new BigDecimal(100), "EUR");

        given(service.findTeamByName(anyString())).willReturn(team);

        mvc.perform(get("/team/name/Angel")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("teamname", is(team.getTeamname())));
        verify(service, VerificationModeFactory.times(1)).findTeamByName("Angel");
        reset(service);
    }

    @Test
    void test_getTeamById() throws Exception {
        TeamEntity team = new TeamEntity(1L, "Angel", "Spain", new BigDecimal(100), "EUR");

        given(service.findTeamById(anyLong())).willReturn(team);

        mvc.perform(get("/team/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("teamname", is(team.getTeamname())));
        verify(service, VerificationModeFactory.times(1)).findTeamById(1L);
        reset(service);
    }

    @Test
    void test_deleteTeam() throws Exception {
        TeamEntity team = new TeamEntity(1L, "Angel", "Spain", new BigDecimal(100), "EUR");

        given(service.deleteTeam(anyString())).willReturn(true);

        mvc.perform(delete("/team/Angel")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service, VerificationModeFactory.times(1)).deleteTeam("Angel");
        reset(service);
    }

    @Test
    void test_registerTeamChart() throws Exception {
        TeamChart chart = new TeamChart(Arrays.asList(new String[]{"AA", "BB"}));
        Player player1 = new Player(1L, "AA", "ADAM", "A", 25, "Dare", 0, "abc@i23.com", 34);
        Player player2 = new Player(2L, "BB", "BDAM", "B", 25, "Bare", 0, "xyz@i23.com", 24);

        List<Player> list = new ArrayList<>();
        list.add(player1);
        list.add(player2);

        given(service.buildTeam(chart)).willReturn(list);

        mvc.perform(post("/team/build")
                .contentType(MediaType.APPLICATION_JSON).content(toJson(chart)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].playername", is(player1.getPlayername())));
        verify(service, VerificationModeFactory.times(1)).buildTeam(chart);
        reset(service);
    }

    private static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}