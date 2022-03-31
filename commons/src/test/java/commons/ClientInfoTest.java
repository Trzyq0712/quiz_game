package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientInfoTest {

    public int currentQuestion;
    public Long gameID;

    @BeforeEach
    void init(){
        currentQuestion=0;
        gameID = 1L;
    }
    @Test
    void getCurrentQuestion() {
        ClientInfo c = new ClientInfo(currentQuestion,gameID);
        assertEquals(currentQuestion,c.getCurrentQuestion());
    }

    @Test
    void getGameID() {
        ClientInfo c = new ClientInfo(currentQuestion,gameID);
        assertEquals(gameID,c.getGameID());
    }

    @Test
    void setRound() {
        ClientInfo c = new ClientInfo(currentQuestion,gameID);
        c.setRound(5);
        assertEquals(5,c.getCurrentQuestion());
    }

    @Test
    void setGameID() {
        ClientInfo c = new ClientInfo(currentQuestion,gameID);
        c.setGameID(4L);
        assertEquals(4L,c.getGameID());
    }
}