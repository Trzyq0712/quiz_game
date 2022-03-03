package server.database;

import commons.PlayerScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerScoreRepository extends JpaRepository<PlayerScore, Long> {}
