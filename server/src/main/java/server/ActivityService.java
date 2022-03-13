package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import server.database.ActivityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    /**
     * Constructor for ActivityService.
     *
     * @param activityRepository The repository which stores the activities.
     */
    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * Method for adding a new Activity to the repository.
     *
     * @param activity The activity to be added.
     * @return The activity added with the assigned id.
     */
    Activity addActivity(Activity activity) {
        activityRepository.save(activity);
        return activity;
    }

    /**
     * Get an Activity from the repository by its id.
     *
     * @param id the id to search for.
     * @return the activity if present. Otherwise, an empty Optional.
     */
    Optional<Activity> getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    /**
     * Get all activities that match the description.
     *
     * Gets all activities whose descriptions contain the string pattern.
     * @param descriptionPattern The string to be searched with.
     * @return List of all matching activities.
     */
    List<Activity> getActivitiesByMatchingDescription(String descriptionPattern) {
        return activityRepository.findByDescriptionLike(descriptionPattern);
    }

    /**
     * Method for removing activities by its id.
     *
     * @param id Id of the activity to be removed.
     * @return The activity if it was present. Otherwise, an empty Optional.
     */
    Optional<Activity> removeActivity(Long id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if (optionalActivity.isPresent()) {
            activityRepository.deleteById(id);
        }
        return optionalActivity;
    }

    /**
     * Method for removing activities.
     *
     * @param activity The activity to be removed from the repository.
     * @return The activity if it was present. Otherwise, an empty Optional.
     */
    Optional<Activity> removeActivity(Activity activity) {
        Optional<Activity> optionalActivity = activityRepository.findOne(Example.of(activity));
        if (optionalActivity.isPresent()) {
            activityRepository.delete(activity);
        }
        return optionalActivity;
    }


}
