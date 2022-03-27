package server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PreGameControllerTest {

    PreGameController sut;
    Player p1;
    Player p2;
    List<Player> playerList;
    DeferredResult<List<Player>> updatedList;
    ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        sut = new PreGameController();
        p1 = new Player("Reinier");
        p2 = new Player("Mana");
        playerList = new ArrayList<>();
        updatedList = sut.updates(playerList);
        mapper = new ObjectMapper();
    }
    @Test
    void playSingleTest() {
        assertTrue(sut.playSingle(p1.getPlayerName()).getBody());
    }

    @Test
    void playMultiNameNotTakenTest() {
        assertTrue(sut.playMulti(p1.getPlayerName()).getBody());
        assertTrue(sut.playMulti(p2.getPlayerName()).getBody());
    }

    @Test
    void playMultiNameTakenTest() {
        sut.playMulti(p1.getPlayerName());
        sut.playMulti(p2.getPlayerName());
        assertFalse(sut.playMulti(p1.getPlayerName()).getBody());
        assertFalse(sut.playMulti(p2.getPlayerName()).getBody());
    }

    @Test
    void getWaitingroomTest() {
        sut.playMulti(p1.getPlayerName());
        playerList.add(p1);
        assertEquals(playerList, sut.getWaitingroom().getBody());
        sut.playMulti(p2.getPlayerName());
        playerList.add(0, p2);
        assertEquals(playerList, sut.getWaitingroom().getBody());
    }

    @Test
    void leaveWaitingroomTest() {
        assertTrue(sut.playMulti(p1.getPlayerName()).getBody());
        sut.leaveWaitingroom(p1);
        assertEquals(playerList, sut.getWaitingroom().getBody());
    }

    /*@Test
    void updatesJoinTest() throws InterruptedException {
        sut.playMulti(p1.getPlayerName());
        Thread.sleep(6000);
        playerList.add(p1);
        sut.playMulti(p2.getPlayerName());
        Thread.sleep(6000);
        playerList.add(0, p2);
        List<Player> result = mapper.convertValue(updatedList.getResult(),new TypeReference<List<Player>>() { });
        assertEquals(playerList, result);
    }

    @Test
    void updatesLeaveTest() throws InterruptedException {
        sut.playMulti(p1.getPlayerName());
        Thread.sleep(6000);
        playerList.add(p1);
        sut.playMulti(p2.getPlayerName());
        Thread.sleep(6000);
        playerList.add(0, p2);
        sut.leaveWaitingroom(p2);
        Thread.sleep(6000);
        playerList.remove(p2);
        List<Player> result = mapper.convertValue(updatedList.getResult(),new TypeReference<List<Player>>() { });
        assertEquals(playerList, result);
    }*/
}