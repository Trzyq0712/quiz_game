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
        g = new Game();
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

//    @Test
//    void getTop() {
//        g.addAPlayer(p2);
//        g.addAPlayer(p1);
//        PlayerScore a = new PlayerScore("a",400);
//        PlayerScore b =new PlayerScore("b",300);
//        PlayerScore c = new PlayerScore("c",200);
//        g.addAPlayer(b);
//        g.addAPlayer(new PlayerScore("d",100));
//        g.addAPlayer(new PlayerScore("c",200));
//        g.addAPlayer(a);
//        List<PlayerScore> playerScoreList = new ArrayList<>();
//        playerScoreList.add(a);
//        playerScoreList.add(b);
//        playerScoreList.add(c);
//        assertEquals(playerScoreList, cp.getTop(3).getBody());
//    }

    @Test
    void add() {
        assertEquals(p1,cp.add(p1).getBody());
    }

    @Test
    void addPointsToAPlayer(){
        g.addAPlayer(p1);
        PlayerScore ptest = p1;
        ptest.addPoints(100);
        assertEquals(ptest,cp.addPointsToAPlayer("Reinier",100).getBody());

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