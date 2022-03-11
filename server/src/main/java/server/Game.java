package server;

import commons.PlayerScore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {

    private List<PlayerScore> players;

    /**
     * Initializes the player's list
     */
    public Game() {
        this.players = new ArrayList<>();
    }

    /**
     * Adds a player to the ones currently in the Game
     * @param player - player that is added
     */
    public void addAPlayer(PlayerScore player){
        players.add(player);
    }

    /**
     * Removes a player from the game
     * @param player - player we want to remove
     * @return true if player has been removed,
     *          false if the player is not in Game
     */
    public boolean removeAPlayer(PlayerScore player){
        if(players.contains(player)){
            players.remove(player);
            return true;
        } else{
            return false;
        }
    }

    /**
     * Getter
     * @return all the players in the game
     */
    public List<PlayerScore> getPlayers() {
        return players;
    }

    /**
     * Set the list of players toa  pre-defined one
     * @param players - the list of players
     */
    public void setPlayers(List<PlayerScore> players) {
        this.players = players;
    }

    /**
     * Compare whether two instances of a Game are equal
     * All fields have to be equal for equality
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
     * @return - the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(players);
    }

    /**
     * Get a string representation of the Game instance
     * It is given in a multiline format
     * @return - the string representation of Game
     */
    @Override
    public String toString() {
        return "Game{" +
                "players=" + players +
                '}';
    }
}
