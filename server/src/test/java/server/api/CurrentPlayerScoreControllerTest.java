package server.api;

import commons.PlayerScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Game;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CurrentPlayerScoreControllerTest {

    CurrentPlayerScoreController cp;
    PlayerScore p1;
    PlayerScore p2;
    Game g;

    @BeforeEach
    public void setup(){
        g = new Game(1);
        cp = new CurrentPlayerScoreController(g);
        p1 = new PlayerScore("Reinier", 50);
        p2 = new PlayerScore("Laimonas",80);
    }

    @Test
    void getAll() {
        g.addAPlayer(p1);
        List<PlayerScore> playerScoreList = new ArrayList<>();
        playerScoreList.add(p1);
        assertEquals(playerScoreList,cp.getAll().getBody());
    }

    @Test
    void getByPlayer() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        assertEquals(p1, cp.getByPlayer(p1.getPlayerName()).getBody());
    }

    @Test
    void getTop() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        g.addAPlayer(new PlayerScore("b",300));
        g.addAPlayer(new PlayerScore("d",100));
        g.addAPlayer(new PlayerScore("c",200));
        g.addAPlayer(new PlayerScore("a",400));
        List<PlayerScore> playerScoreList = new ArrayList<>();
        playerScoreList.add(new PlayerScore("a",400));
        playerScoreList.add(new PlayerScore("b",300));
        playerScoreList.add(new PlayerScore("c",200));
        assertEquals(playerScoreList, cp.getTop(3).getBody());
    }

    @Test
    void add() {
        assertEquals(p1,cp.add(p1).getBody());
    }

    @Test
    void deleteAll() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        List<PlayerScore> playerScoreList = new ArrayList<>();
        assertEquals(true,cp.deleteAll().getBody());
        assertEquals(playerScoreList, cp.getAll().getBody());
    }

    @Test
    void deleteByID() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        List<PlayerScore> playerScoreList = new ArrayList<>();
        playerScoreList.add(p2);
        assertEquals(true, cp.deleteByName(p1.getPlayerName()).getBody());
        assertEquals(playerScoreList,cp.getAll().getBody());
    }
}