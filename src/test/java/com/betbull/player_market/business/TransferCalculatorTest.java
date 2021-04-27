package com.betbull.player_market.business;

import com.betbull.player_market.modal.ContractFee;
import com.betbull.player_market.modal.Player;
import com.betbull.player_market.modal.TeamEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransferCalculatorTest {

    @InjectMocks
    private TransferCalculator calculator;

    @Test
    void calculatePlayerContractFee() {
        Player player = new Player(1L, "AA", "ADAM", "A", 25, "Dare", 0, "abc@i23.com", 34);
        TeamEntity team = new TeamEntity(1L, "Angel", "Spain", new BigDecimal(100), "EUR");

        ContractFee response = calculator.calculatePlayerContractFee(player, team);
        assertNotNull(response);
        assertEquals(player.getPlayername(), response.getPlayername());
        assertEquals(team.getCurrency(), response.getCurrency());
        assertTrue(response.getCommisionfee().intValue() > 0);
    }

    @Test
    void transferFeeCalculator() {
        BigDecimal val = calculator.transferFeeCalculator(25, 30);
        assertNotNull(val);
        assertEquals(0, val.compareTo(new BigDecimal("83333.33")));

    }
}