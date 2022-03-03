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

    public PlayerScore(String playerName, int score, Timestamp time) {
        this.playerName = playerName;
        this.score = score;
        this.time = time;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}