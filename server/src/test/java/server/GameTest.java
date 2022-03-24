package server;

import commons.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game g;
    Player p1;
    Player p2;

    @BeforeEach
    public void setup() {
        g = new Game();
        p1 = new Player("Vian",50);
        p2 = new Player("Kuba", 70);
    }

    @Test
    void addAPlayer() {
        g.addAPlayer(p1);
        assertTrue(g.getSize()==1);

    }

    @Test
    void addPointsToAPlayer(){
        g.addAPlayer(p1);
        Player ptest= p1;
        p1.addPoints(50);
        assertEquals(ptest,g.addPointsToAPlayer("Vian",50));
    }

    @Test
    void removeAPlayerWithId() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        g.removeAPlayerWithName(p1.getPlayerName());
        List<Player> PlayerList = new ArrayList<>();
        PlayerList.add(p2);
        assertEquals(g.getPlayers(), PlayerList);
    }


    @Test
    void removeAll() {
        g.addAPlayer(p1);
        g.addAPlayer(p2);
        g.removeAll();
        List<Player> PlayerList = new ArrayList<>();
        assertEquals(g.getPlayers(), PlayerList);
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
        List<Player> PlayerList = new ArrayList<>();
        PlayerList.add(p1);
        PlayerList.add(p2);
        assertEquals(g.getPlayers(),PlayerList);
    }

    @Test
    void getByName() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        Player ptest = g.getByName(p1.getPlayerName());
        assertEquals(p1, ptest);
    }

    @Test
    void setPlayers() {
        List<Player> PlayerList = new ArrayList<>();
        PlayerList.add(p1);
        PlayerList.add(p2);
        g.setPlayers(PlayerList);
        assertEquals(g.getPlayers(),PlayerList);
    }

//    @Test
//    void getGameId() {
//        assertTrue(g.getGameId()==1);
//    }

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
        List<Player> PlayerList = new ArrayList<>();
        PlayerList.add(p2);
        PlayerList.add(p1);
        String outcome = "Game 0{players=" + PlayerList + '}';
        assertEquals(outcome,g.toString());
    }
}