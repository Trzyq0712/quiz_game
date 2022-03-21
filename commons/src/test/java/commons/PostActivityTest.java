package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PostActivityTest {

    Activity activity;
    private File picture;
    String writeTo;
    PostActivity postActivity;

    @BeforeEach
    void setUp() {
        activity = new Activity("Description", 42L, "path\\file.png");
        picture = new File(activity.getPicturePath());
        writeTo = "path\\newFile.png";
        postActivity = new PostActivity(activity, writeTo);
    }

    @Test
    void getPicture() {
        assertEquals(picture, postActivity.getPicture());
    }

    @Test
    void getActivity() {
        assertEquals(activity, postActivity.getActivity());
    }

    @Test
    void getWriteTo() {
        assertEquals(writeTo, postActivity.writeTo);
    }

    @Test
    void testEquals() {
        PostActivity postActivity2 = new PostActivity(activity, writeTo);
        assertEquals(postActivity2, postActivity);
    }
}