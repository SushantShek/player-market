package com.betbull.player_market.repository;

import com.betbull.player_market.modal.Player;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

    @Repository
    public interface PlayerRepository extends JpaRepository<Player, Long> {

        public Player findPlayerByPlayername(String name);
    }
