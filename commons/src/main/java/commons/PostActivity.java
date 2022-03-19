package commons;

import java.io.File;
import java.util.Objects;

public class PostActivity{

    Activity activity;
    private File picture;

    public PostActivity(Activity activity) {
        this.activity = activity;
        this.picture = new File(activity.getPicturePath());
    }

    public PostActivity() {
        super();
    }

    public File getPicture() {
        return picture;
    }

    public Activity getActivity() {
        return activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostActivity)) return false;
        PostActivity that = (PostActivity) o;
        return Objects.equals(getActivity(), that.getActivity()) && Objects.equals(getPicture(), that.getPicture());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getActivity(), getPicture());
    }
}
