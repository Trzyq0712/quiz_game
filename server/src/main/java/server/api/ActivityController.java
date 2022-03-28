package server.api;

import commons.PostActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import commons.Activity;
import server.ActivityService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Random;

/**
 * Controller for managing activities.
 */
@RestController
@RequestMapping("/api/activity")
public class ActivityController extends BaseController {

    private int currentRound = -1; // -1 means there's no game active
    private int currentQuestionType = -1;
    private List<Activity> currentListOfActivities;
    public String imgPath = getClass().getClassLoader().getResource("static/")
            .toString().substring(6).replace("%20", " ");


    /**
     * Endpoint for ActivityController.
     *
     * @param activityService The service for managing activites.
     */
    @Autowired
    public ActivityController(ActivityService activityService) {
        super(activityService);
    }

    /**
     * Endpoint for getting activities.
     * If specified RequestParam present (q=pattern), return activities whose
     * description match the pattern.
     *
     * @param pattern The pattern to search for in activities. Ignored if null.
     * @return The list of all matching activities.
     */
    @GetMapping(path = "")
    public ResponseEntity<List<Activity>> getActivities(
            @RequestParam(value = "q", required = false) String pattern) {
        if (pattern != null) {
            return ResponseEntity.ok(
                    activityService.getActivitiesByMatchingDescription(pattern.toLowerCase()));
        }
        return ResponseEntity.ok(activityService.getAllActivities());
    }

    /**
     * Endpoint for getting 3 activities
     * @return The list containing 3 activities
     */
    @GetMapping(path = "3")
    public ResponseEntity<List<Activity>> get3Activities(){
         return ResponseEntity.ok(currentListOfActivities);
    }


    @PostMapping(path = "getQuestion")
    public ResponseEntity<Integer> getQuestionType(@RequestBody int round) {
        if (currentRound == -1 || round == currentRound + 1) {//either the first request or a request for a new round
            currentQuestionType = (int)(Math.random()*3);
            currentRound = round;
            currentListOfActivities = activityService.get3Activities();
        }
        return ResponseEntity.ok(currentQuestionType);
    }

    /**
     * Endpoint for getting an activities
     * @return The activity
     */
    @GetMapping(path = "1")
    public ResponseEntity<Activity> getActivity(){
        return ResponseEntity.ok(activityService.getActivity());
    }

    /**
     * Endpoint for getting activities by id.
     *
     * @param id The id of the activity.
     * @return The activity if present. Otherwise, a NOT_FOUND response.
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable("id") Long id) {
        var activity = activityService.getActivityById(id);
        if (activity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(activity.get());
    }

    /**
     * Endpoint for deleting activities by id.
     *
     * @param id The id of the activity.
     * @return The activity removed if was present. Otherwise, a NOT_FOUND response.
     */
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Activity> deleteActivityById(@PathVariable("id") Long id) {
        var activity = activityService.removeActivity(id);
        if (activity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(activity.get());
    }

    /**
     * Endpoint for adding new activities.
     *
     * @param activity The activity to be added.
     * @return The activity added to the database.
     */
    @PostMapping(path = "")
    public ResponseEntity<Activity> addActivity(@RequestBody Activity activity) {
        return ResponseEntity.ok(activityService.addActivity(activity));
    }

    /**
     * Endpoint for adding new activities.
     *
     * @param postActivity The activity with an image to be added.
     * @return The activity added to the database.
     */
    @PostMapping(path = "/add")
    public ResponseEntity<Activity> addPostActivity(@RequestBody PostActivity postActivity) {
        if(writeImageToFile(postActivity))
            return ResponseEntity.ok(activityService.addActivity(postActivity.getActivity()));

        return ResponseEntity.ok(null);
    }

    /**
     * Writes the image to the folder
     *
     * @param postActivity activity that has the image to be written to the folder newActivities
     * @return true if written successfully
     */
    public boolean writeImageToFile(PostActivity postActivity){
        try {
            BufferedImage image = ImageIO.read(postActivity.getPicture());
            Activity activity = postActivity.getActivity();
            String extension = activity.getPicturePath().substring(activity.getPicturePath().length()-3);

            String path;
            do path = new File(postActivity.getWriteTo()
                    + new Random().nextInt() + "." + extension).getAbsolutePath();
            while (new File(path).isFile());

            activity.setPicturePath(path);
            ImageIO.write(image, extension, new File(path));
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }
}
