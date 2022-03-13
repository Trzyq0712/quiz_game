package server.database;

import org.springframework.data.jpa.repository.JpaRepository;
import server.Activity;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    /**
     * Find all activities that match the description.
     *
     * @param description string to search for.
     * @return list of all activities matching the string.
     */
    List<Activity> findByDescriptionLike(String description);
}
