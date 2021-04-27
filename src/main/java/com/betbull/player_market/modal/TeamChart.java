package com.betbull.player_market.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class TeamChart {

    @JsonProperty("players")
    @NonNull
    private List<String> players;

}
