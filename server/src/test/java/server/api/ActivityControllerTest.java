package server.api;

import commons.Activity;
import commons.PostActivity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.ActivityService;
import server.MockActivityRepository;
import server.database.ActivityRepository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ActivityControllerTest {

    private ActivityRepository repo;
    private ActivityService serv;
    private ActivityController sut;
    private Activity act1;
    private Activity act2;
    private Activity act3;
    private PostActivity postAct;

    @BeforeEach
    void setup() {
        repo = new MockActivityRepository();
        serv = new ActivityService(repo);
        sut = new ActivityController(serv);
        sut.setImagePath("src\\test\\resources\\static\\");
        act1 = new Activity("description", 12L, "path/to/file1");
        act2 = new Activity("a different description", 42L, "path/to/file2");
        act3 = new Activity("even more different description", 50L,
                "\\activity\\00\\fridge.png");
        File pathToFile = new File("src\\test\\resources\\static\\" + act3.getPicturePath());
        System.out.println("trying to fetch image: "+ pathToFile.getAbsolutePath());
        try{
            byte[] bytes = Files.readAllBytes(Paths.get(pathToFile.getAbsolutePath()));
            postAct = new PostActivity(act3, bytes);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Test
    void getActivities() {
        serv.addActivity(act1);
        assertEquals(ResponseEntity.of(Optional.of(List.of(act1))), sut.getActivities(null));
        serv.addActivity(act2);
        assertEquals(2, sut.getActivities(null).getBody().size());
        assertEquals(ResponseEntity.of(Optional.of(List.of(act2))), sut.getActivities("different"));
        assertEquals(2, sut.getActivities("description").getBody().size());
        assertEquals(0, sut.getActivities("other").getBody().size());
    }

    @Test
    void getActivityById() {
        var act = serv.addActivity(act1);
        assertEquals(act1, sut.getActivityById(act.getId()).getBody());
        assertEquals(HttpStatus.NOT_FOUND, sut.getActivityById(act.getId() + 1).getStatusCode());
    }

    @Test
    void deleteActivityById() {
        serv.addActivity(act1);
        serv.addActivity(act2);
        assertEquals(ResponseEntity.of(Optional.of(act1)), sut.deleteActivityById(act1.getId()));
        assertEquals(HttpStatus.NOT_FOUND, sut.deleteActivityById(act1.getId()).getStatusCode());
        assertEquals(1, repo.count());
    }

    @Test
    void addActivity() {
        sut.addActivity(act2);
        assertTrue(repo.findAll().contains(act2));
        var res = sut.addActivity(act2);
        assertEquals(ResponseEntity.of(Optional.of(act2)), res);
        assertEquals(2, repo.count());
    }

    @Test
    void addDeletePostActivity() {
        Activity serverAct = sut.addPostActivity(postAct).getBody();
        assertTrue(repo.findAll().contains(postAct.getActivity()));
        var res = sut.addActivity(postAct.getActivity());
        assertEquals(ResponseEntity.of(Optional.of(postAct.getActivity())), res);
        assertEquals(2, repo.count());
        assertTrue(sut.deletePostActivity(serverAct.getId()).getBody());
        assertEquals(1, repo.count());
    }
}