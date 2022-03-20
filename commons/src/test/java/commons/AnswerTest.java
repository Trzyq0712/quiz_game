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
}