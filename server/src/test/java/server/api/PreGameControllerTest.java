package server.api;

import commons.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PreGameControllerTest {

    PreGameController sut;
    Player p1;
    Player p2;
    List<Player> playerList;

    @BeforeEach
    public void setup() {
        sut = new PreGameController();
        p1 = new Player("Reinier");
        p2 = new Player("Mana");
        playerList = new ArrayList<>();
    }
    @Test
    void playSingleTest() {
        assertTrue(sut.playSingle(p1.name).getBody());
    }

    @Test
    void playMultiNameNotTakenTest() {
        assertTrue(sut.playMulti(p1.name).getBody());
        assertTrue(sut.playMulti(p2.name).getBody());
    }

    @Test
    void playMultiNameTakenTest() {
        sut.playMulti(p1.name);
        sut.playMulti(p2.name);
        assertFalse(sut.playMulti(p1.name).getBody());
        assertFalse(sut.playMulti(p2.name).getBody());
    }

    @Test
    void getWaitingroomTest() {
        sut.playMulti(p1.name);
        playerList.add(p1);
        assertEquals(playerList, sut.getWaitingroom().getBody());
        sut.playMulti(p2.name);
        playerList.add(p2);
        assertEquals(playerList, sut.getWaitingroom().getBody());
    }
}