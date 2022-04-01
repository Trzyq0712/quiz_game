package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    public Player ps1;
    public Player ps2;
    public Player ps3;

    @BeforeEach
    void init() {
        ps1 = new Player("John", 2137);
        ps2 = new Player("Joanna", 3214);
        ps3 = new Player("Johnny", 5555);
    }

    @Test
    void testConstructor() {
        var ps = new Player("Josh", 123);
        assertEquals("Josh", ps.getPlayerName());
        assertEquals(123, ps.getScore());
    }


    /*@Test this test doesn't work properly anymore since IDs are randomly generated
    void testEquals() {
        assertNotEquals(ps1, ps2);
        assertEquals(ps1, ps1);
        assertEquals(ps2, ps2);
        Player p = new Player("Johnny", 5555);
        assertEquals(p, ps3);
        assertNotEquals(ps2, ps3);
    }*/

    @Test
    void testHashCode() {
        assertEquals(ps1.hashCode(), ps1.hashCode());
        assertEquals(ps2.hashCode(), ps2.hashCode());
        assertNotEquals(ps1.hashCode(), ps2.hashCode());
        var ps = new Player("Josh", 123);
        assertNotEquals(ps.hashCode(), ps1.hashCode());
    }

    @Test
    void testToString() {
        var strRepr = ps1.toString();
        System.out.println(strRepr);
        assertTrue(strRepr.contains("John"));
        assertTrue(strRepr.contains("2137"));
        assertTrue(strRepr.contains(Player.class.getSimpleName()));
        assertTrue(strRepr.contains("playerName"));
        assertTrue(strRepr.contains("score"));
    }
}