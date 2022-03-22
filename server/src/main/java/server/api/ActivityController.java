package server.api;

import commons.PostActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import commons.Activity;
import server.ActivityService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Controller for managing activities.
 */
@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    private final ActivityService activityService;
    public String imgPath = getClass().getClassLoader().getResource("static/")
            .toString().substring(6).replace("%20", " ");

    /**
     * Endpoint for ActivityController.
     *
     * @param activityService The service for managing activites.
     */
    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * @return current image path of the server
     */
    public String getImagePath() {
        return imgPath;
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
         return ResponseEntity.ok(activityService.get3Activities());
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
     * Endpoint for adding new activities and their image.
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
     * Endpoint for updating new activities.
     *
     * @param postActivity The activity with an image to be updated
     * @return The activity added to the database.
     */
    @PostMapping(path = "/update")
    public ResponseEntity<Activity> updatePostActivity(@RequestBody PostActivity postActivity) {
        if(overWriteImage(postActivity))
            return ResponseEntity.ok(activityService.updateActivity(postActivity.getActivity()));

        return ResponseEntity.ok(null);
    }

    /**
     * Endpoint for deleting activities and their images
     *
     * @param id of the activity to be deleted
     * @return the image file if it is found
     */
    @PostMapping(path = "/delete")
    public ResponseEntity<Boolean> deletePostActivity(@RequestBody Long id) {
        Optional<Activity> activity = activityService.getActivityById(id);
        if(activity.isEmpty())
            return ResponseEntity.ok(false);

        Path pathToFile = Path.of(imgPath, activity.get().getPicturePath());
        File toBeDeleted = new File(pathToFile.toString());
        System.out.println("trying to delete: " + pathToFile);
        try{
            if(activityService.removeActivity(id).isPresent()){
                toBeDeleted.delete();
                return ResponseEntity.ok(true);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return ResponseEntity.ok(false);
    }

    /**
     * Writes the image to the folder
     *
     * @param postActivity activity that has the image to be written to the folder newActivities
     * @return true if written successfully
     */
    public boolean writeImageToFile(PostActivity postActivity){
        try {
            Activity activity = postActivity.getActivity();
            String extension = activity.getPicturePath().substring(activity.getPicturePath().length()-3);

            Path pathToFile;
            String fileName;
            do {
                fileName = new Random().nextInt() + "." + extension;
                pathToFile = Path.of(imgPath,"activity", "newActivities", fileName);
            }
            while (new File(pathToFile.toString()).isFile());

            System.out.println("trying to write: " + pathToFile);
            Files.write(pathToFile, postActivity.getPictureBuffer());
            activity.setPicturePath("activity/newActivities/" + fileName);
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }

    /**
     * Rewrites the image to the folder
     *
     * @param postActivity activity that has the image to be rewritten
     * @return true if rewritten successfully
     */
    public boolean overWriteImage(PostActivity postActivity){
        try {
            Activity activity = postActivity.getActivity();
            Activity oldActivity = activityService.getActivityById(activity.getId()).get();

            Path path = Path.of(imgPath, oldActivity.getPicturePath());
            System.out.println("trying to overwrite: " + path);
            Files.write(path, postActivity.getPictureBuffer());
            activity.setPicturePath(oldActivity.getPicturePath());
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }
}
