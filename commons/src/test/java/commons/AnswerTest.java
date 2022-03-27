package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    @Test
    void getAnswer() {
        Answer a = new Answer(1,5050);
        assertEquals(1,a.getAnswer());
    }

    @Test
    void getTimeToAnswer() {
        Answer a = new Answer(1,5050);
        assertEquals(5050, a.getTimeToAnswer());
    }

    @Test
    void testEquals() {
        Answer a = new Answer(1,5050);
        Answer b = new Answer(1,5050);
        assertTrue(a.equals(b));
    }

    @Test
    void testNotEqualsTime(){
        Answer a = new Answer(1,5050);
        Answer b = new Answer(1,5051);
        assertFalse(a.equals(b));
    }

    @Test
    void testNotEqualsAnswer(){
        Answer a = new Answer(2,5050);
        Answer b = new Answer(1,5050);
        assertFalse(a.equals(b));
    }

    @Test
    void testPoints(){
        Answer a = new Answer(1,5000);
        assertEquals(150,a.getPoints());
        Answer b = new Answer(1,0);
        assertEquals(200,b.getPoints());
        Answer c = new Answer(1,10000);
        assertEquals(100,c.getPoints());
    }
}