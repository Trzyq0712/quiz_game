package server;

import commons.PlayerScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game g;
    PlayerScore p1;
    PlayerScore p2;

    @BeforeEach
    public void setup() {
        g = new Game();
        p1 = new PlayerScore("Vian",50);
        p2 = new PlayerScore("Kuba", 70);
    }

    @Test
    void addAPlayer() {
        g.addAPlayer(p1);
        assertTrue(g.getSize()==1);

    }

    @Test
    void addPointsToAPlayer(){
        g.addAPlayer(p1);
        PlayerScore ptest= p1;
        p1.addPoints(50);
        assertEquals(ptest,g.addPointsToAPlayer("Vian",50));
    }

    @Test
    void removeAPlayerWithId() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        g.removeAPlayerWithName(p1.getPlayerName());
        List<PlayerScore> playerScoreList = new ArrayList<>();
        playerScoreList.add(p2);
        assertEquals(g.getPlayers(), playerScoreList);
    }


    @Test
    void removeAll() {
        g.addAPlayer(p1);
        g.addAPlayer(p2);
        g.removeAll();
        List<PlayerScore> playerScoreList = new ArrayList<>();
        assertEquals(g.getPlayers(), playerScoreList);
    }

    @Test
    void getSize() {
        g.addAPlayer(p1);
        assertTrue(g.getSize()==1);
        g.addAPlayer(p2);
        assertTrue(g.getSize()==2);
    }

    @Test
    void getPlayers() {
        g.addAPlayer(p1);
        g.addAPlayer(p2);
        List<PlayerScore> playerScoreList = new ArrayList<>();
        playerScoreList.add(p1);
        playerScoreList.add(p2);
        assertEquals(g.getPlayers(),playerScoreList);
    }

    @Test
    void getByName() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        PlayerScore ptest = g.getByName(p1.getPlayerName());
        assertEquals(p1, ptest);
    }

    @Test
    void setPlayers() {
        List<PlayerScore> playerScoreList = new ArrayList<>();
        playerScoreList.add(p1);
        playerScoreList.add(p2);
        g.setPlayers(playerScoreList);
        assertEquals(g.getPlayers(),playerScoreList);
    }

    @Test
    void getGameId() {
        assertTrue(g.getGameId()==1);
    }

    @Test
    void testEquals() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        Game g1 = new Game();
        g1.addAPlayer(p2);
        g1.addAPlayer(p1);
        assertTrue(g.equals(g1));
    }

    @Test
    void testHashCode() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        Game g1 = new Game();
        g1.addAPlayer(p2);
        g1.addAPlayer(p1);
        assertEquals(g.hashCode(),g1.hashCode());
        Game g2 = new Game();
        g1.addAPlayer(p2);
        g1.addAPlayer(p1);
        assertNotEquals(g.hashCode(), g2.hashCode());
    }

    @Test
    void testToString() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        List<PlayerScore> playerScoreList = new ArrayList<>();
        playerScoreList.add(p2);
        playerScoreList.add(p1);
        String outcome = "Game 1{players=" + playerScoreList + '}';
        assertEquals(outcome,g.toString());
    }
}