package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerScoreTest {

    public PlayerScore ps1;
    public PlayerScore ps2;
    public PlayerScore ps3;

    @BeforeEach
    void init() {
        ps1 = new PlayerScore("John", 2137);
        ps2 = new PlayerScore("Joanna", 3214);
        ps3 = new PlayerScore(0, "Johnny", 5555);
    }

    @Test
    void testConstructor() {
        var ps = new PlayerScore("Josh", 123);
        assertEquals("Josh", ps.getPlayerName());
        assertEquals(123, ps.getScore());
    }

    @Test
    void test2ndConstructor() {
        var ps = new PlayerScore(1,"Josh", 123);
        assertEquals(1,ps.getRank());
        assertEquals("Josh", ps.getPlayerName());
        assertEquals(123, ps.getScore());
    }

    @Test
    void testEquals() {
        assertNotEquals(ps1, ps2);
        assertEquals(ps1, ps1);
        assertEquals(ps2, ps2);
        PlayerScore p = new PlayerScore(0, "Johnny", 5555);
        assertEquals(p,ps3);
        assertNotEquals(ps2,ps3);
    }

    @Test
    void testHashCode() {
        assertEquals(ps1.hashCode(), ps1.hashCode());
        assertEquals(ps2.hashCode(), ps2.hashCode());
        assertNotEquals(ps1.hashCode(), ps2.hashCode());
        var ps = new PlayerScore("Josh", 123);
        assertNotEquals(ps.hashCode(), ps1.hashCode());
    }

    @Test
    void testToString() {
        var strRepr = ps1.toString();
        System.out.println(strRepr);
        assertTrue(strRepr.contains("John"));
        assertTrue(strRepr.contains("2137"));
        assertTrue(strRepr.contains(PlayerScore.class.getSimpleName()));
        assertTrue(strRepr.contains("playerName"));
        assertTrue(strRepr.contains("score"));
    }
}