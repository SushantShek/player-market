package com.betbull.player_market.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Player
 */

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Player {
    @JsonProperty("id")
    @Id
    private Long id;

    @JsonProperty("playername")
    private String playername;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("team")
    private String team;

    @JsonProperty("playerstatus")
    private Integer playerstatus;

    @JsonProperty("email")
    private String email;

    @JsonProperty("experience")
    private int experience;

}

