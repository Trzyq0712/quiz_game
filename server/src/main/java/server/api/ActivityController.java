package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import commons.Activity;
import server.ActivityService;

import java.util.List;

/**
 * Controller for managing activities.
 */
@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    private final ActivityService activityService;

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

}
