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
    public long id;

    public String playerName;
    public int score;
    public Timestamp time = Timestamp.from(Instant.now());

    @SuppressWarnings("unused")
    private PlayerScore() {
        // for object mapper
    }

    /**
     * Create a playerScore instance
     * @param playerName - name of the player
     * @param score - the score they scored in the game
     */
    public PlayerScore(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    /**
     * Updating the score by adding points to it
     * @param points - points we wish to add to the player's score
     */
    public void addPoints(int points){
        score+=points;
    }

    public long getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public Timestamp getTime() {
        return time;
    }

    /**
     * Compare whether two instances of a PlayerScore are equal
     * All fields have to be equal for equality
     * @param obj - to be compared with
     * @return - whether the two objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * Get a hash code of the PlayerScore instance
     * @return - the hash code
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Get a string representation of the PlayerScore instance
     * It is given in a multiline format
     * @return - the string representation of PlayerScore
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}