package com.betbull.player_market.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Team
 */
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TeamEntity {

    @JsonProperty("id")
    @Id
    private Long id;

    @JsonProperty("teamname")
    private String teamname;

    @JsonProperty("location")
    private String location;

    @JsonProperty("fee")
    private BigDecimal fee;

    @JsonProperty("currency")
    private String currency;



}