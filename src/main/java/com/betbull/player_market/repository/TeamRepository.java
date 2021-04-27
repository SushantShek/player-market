package com.betbull.player_market.repository;

import com.betbull.player_market.modal.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
   public TeamEntity findTeamEntityByTeamname(String name);
}
