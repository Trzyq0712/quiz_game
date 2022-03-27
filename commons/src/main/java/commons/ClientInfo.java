package commons;

import java.io.Serializable;

public class ClientInfo implements Serializable {
    private int currentQuestion;
    private Long gameID;

    public ClientInfo() {
    }

    public ClientInfo(int currentQuestion, Long gameID) {
        this.currentQuestion = currentQuestion;
        this.gameID = gameID;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public Long getGameID() {
        return gameID;
    }

    public void setRound(int round) {
        this.currentQuestion = currentQuestion;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }
}
