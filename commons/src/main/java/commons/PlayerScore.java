package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * A class to store scores of players in the database
 * An instance consists of a player name, the score obtained and the time it was achieved
 */
@Table
@Entity
public class PlayerScore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int rank = 0;

    private String playerName;
    private Integer score;
    private Timestamp time;

    @SuppressWarnings("unused")
    private PlayerScore() {
        // for object mapper
    }

    /**
     * Create a playerScore instance.
     *
     * @param playerName name of the player.
     * @param score the score they scored in the game.
     */
    public PlayerScore(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
        time = Timestamp.from(Instant.now());
    }

    /**
     * Create a playerScore instance with ranking.
     * @param rank rank number of the player
     * @param playerName name of the player.
     * @param score the score they scored in the game.
     */
    public PlayerScore(int rank, String playerName, int score) {
        this.rank=rank;
        this.playerName = playerName;
        this.score = score;
    }


    /**
     * Updating the score by adding points to it.
     *
     * @param points to be added to the player's score.
     */
    public void addPoints(int points){
        score += points;
    }

    /**
     * Getter for the PlayerScore's id.
     *
     * @return the id of the PlayerScore instance.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the PlayerScore's id.
     *
     * @param id new id to be set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for player's name.
     *
     * @return the name of the player.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Getter for player's score.
     *
     * @return the score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter for the time the score was achieved.
     *
     * @return the time the game was played.
     */
    public Timestamp getTime() {
        return time;
    }

    /**
     * Compare whether two instances of a PlayerScore are equal.
     *
     * All fields have to be equal for equality.
     * @param obj to be compared with.
     * @return whether the two objects are equal.
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * Get a hash code of the PlayerScore instance.
     *
     * @return the hash code of the PlayerScore instance.
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Get a string representation of the PlayerScore instance.
     *
     * It is given in a multiline format.
     * @return the string representation of the PlayerScore instance.
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}