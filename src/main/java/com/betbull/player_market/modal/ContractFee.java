package com.betbull.player_market.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractFee {

    @JsonProperty("playername")
    private String playername;

    @JsonProperty("contractfee")
    private BigDecimal contractfee;

    @JsonProperty("commisionfee")
    private BigDecimal commisionfee;

    @JsonProperty("currency")
    private String currency;

}
