package com.betbull.player_market.business;

import com.betbull.player_market.exception.InvalidInputRequestParamException;
import com.betbull.player_market.modal.ContractFee;
import com.betbull.player_market.modal.Player;
import com.betbull.player_market.modal.TeamEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Component
public class TransferCalculator {

    final BigDecimal FEE_CALCULATOR_CONSTANT = new BigDecimal(100000);

    /**
     * Calculates the Contract fee for transfer of player
     *
     * @param player object
     * @param team   object
     * @return contractfee object
     */
    public ContractFee calculatePlayerContractFee(Player player, TeamEntity team) {
        log.info("Calculate Contract fee for Player experience is {}", player.getPlayername());
        if(ObjectUtils.isEmpty(player) || ObjectUtils.isEmpty(team)){
            throw new InvalidInputRequestParamException("Player OR Team cannot be null or empty");
        }
        ContractFee finalFee = new ContractFee();
        finalFee.setPlayername(player.getPlayername());
        finalFee.setCurrency(team.getCurrency());
        BigDecimal transferFee = transferFeeCalculator(player.getExperience(), player.getAge());
        BigDecimal commission = (transferFee.multiply(BigDecimal.TEN).divide(new BigDecimal(100)));

        finalFee.setCommisionfee(commission);
        finalFee.setContractfee(transferFee.add(commission));
        return finalFee;
    }

    /**
     * Calculate the transfer fee for the contract
     *
     * @param monthOfExperience in int
     * @param age               in int
     * @return big decimal transfer fee
     */
    BigDecimal transferFeeCalculator(int monthOfExperience, int age) {
        log.info("Player experience is {} and age {}", monthOfExperience ,age );
        BigDecimal contractFee = FEE_CALCULATOR_CONSTANT.multiply(new BigDecimal(monthOfExperience));
        return (contractFee.divide(new BigDecimal(age), 2, RoundingMode.HALF_UP));
    }


}
