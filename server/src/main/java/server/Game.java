package server;

import commons.PlayerScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class Game {

    private final int gameId;
    private List<PlayerScore> players;

    /**
     * Constructor of Game
     * gameId - id of the game we are in
     */
    @Autowired
    public Game() {
        this.players = new ArrayList<>();
        this.gameId = 0;
    }

    /**
     * Adds a player to the ones currently in the Game
     *
     * @param player - player that is added
     */
    public PlayerScore addAPlayer(PlayerScore player) {
        players.add(player);
        return player;
    }

    /**
     * Awards points to a player
     *
     * @param name   - name of the player
     * @param amount - amount of points awarded
     * @return the PlayerScore for confirmation
     */
    public PlayerScore addPointsToAPlayer(String name, int amount) {
        if (name != null) {
            PlayerScore p = getByName(name);
            if (p != null) {
                p.addPoints(amount);
            }
            return p;
        }
        return null;
    }

    /**
     * Removes a player from the game
     *
     * @param name - player we want to remove
     * @return true if player has been removed,
     * false if the player is not in Game
     */
    public boolean removeAPlayerWithName(String name) {
        if (name != null) {
            players.remove(getByName(name));
            return true;
        }
        return false;
    }

    public boolean removeAll() {
        int size = players.size();
        for (int index = size - 1; index >= 0; index--) {
            players.remove(index);
        }
        return true;
    }

    /**
     * Gets the size
     *
     * @return the size of the list in Game
     */
    public int getSize() {
        return players.size();
    }

    /**
     * Getter
     *
     * @return all the players in the game
     */
    public List<PlayerScore> getPlayers() {
        return players;
    }

    /**
     * Set the list of players toa  pre-defined one
     *
     * @param players - the list of players
     */
    public void setPlayers(List<PlayerScore> players) {
        this.players = players;
    }

    /**
     * Gets the PlayerScore according to the id
     *
     * @param name - id of the PlayerScore
     * @return the PlayerScore associated with that id
     * null if id doesn't exist.
     */
    public PlayerScore getByName(String name) {
        if (name == null) {
            return null;
        }
        for (PlayerScore p : players) {
            if (p.getPlayerName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Getter for game id
     *
     * @return the game id
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * Compare whether two instances of a Game are equal
     * All fields have to be equal for equality
     *
     * @param o - to be compared with
     * @return - whether the two objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(players, game.players);
    }

    /**
     * Get a hash code of the Game instance
     *
     * @return - the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(players);
    }

    /**
     * Get a string representation of the Game instance
     * It is given in a multiline format
     *
     * @return - the string representation of Game
     */
    @Override
    public String toString() {
        return "Game " + getGameId()
                + "{" +
                "players=" + players
                + '}';
    }
}
