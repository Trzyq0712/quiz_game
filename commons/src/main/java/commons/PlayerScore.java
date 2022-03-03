package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.sql.Timestamp;

/**
 * A class to store scores of players in the database
 * An instance consists of a player name, the score obtained and the time it was achieved
 */
@Entity
public class PlayerScore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String playerName;
    public int score;
    public Timestamp time;

    @SuppressWarnings("unused")
    private PlayerScore() {
        // for object mapper
    }

    /**
     * Create a playerScore instance
     * @param playerName - name of the player
     * @param score - the score they scored in the game
     * @param time - Timestamp when the scored was achieved
     */
    public PlayerScore(String playerName, int score, Timestamp time) {
        this.playerName = playerName;
        this.score = score;
        this.time = time;
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