package server.api;

import commons.PlayerScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerScoreControllerTest {

    private TestPlayerScoreRepository repo;
    private PlayerScoreController sut;

    private PlayerScore ps1;
    private PlayerScore ps2;

    @BeforeEach
    public void setup() {
        repo = new TestPlayerScoreRepository();
        sut = new PlayerScoreController(repo);
        ps1 = new PlayerScore("ps1", 1);
        ps2 = new PlayerScore("ps2", 2);
    }

    @Test
    void testRepoUsed() {
        sut.add(ps1);
        assertTrue(repo.calledMethods.contains("save"));
    }

    @Test
    void testAdd() {
        sut.add(ps1);
        repo.exists(Example.of(ps1));
    }

    @Test
    void testGetAll() {
        sut.add(ps1);
        sut.add(ps2);
        var scores = sut.getAll().getBody();
        assertTrue(scores.contains(ps1));
        assertTrue(scores.contains(ps2));
    }

    @Test
    void testGetById() {
        sut.add(ps1);
        sut.add(ps2);
        assertEquals(ps1, sut.getById(ps1.id).getBody());
        assertEquals(ps2, sut.getById(ps2.id).getBody());
    }

    @Test
    void testGetTop() {
        PlayerScore ps3 = new PlayerScore("ps3", 3);
        sut.add(ps1);
        sut.add(ps3);
        sut.add(ps2);
        assertEquals(List.of(), sut.getTop(0).getBody());
        assertEquals(List.of(ps3), sut.getTop(1).getBody());
        assertEquals(List.of(ps3, ps2), sut.getTop(2).getBody());
        assertEquals(List.of(ps3, ps2, ps1), sut.getTop(3).getBody());
        assertEquals(List.of(ps3, ps2, ps1), sut.getTop(4).getBody());
    }
}