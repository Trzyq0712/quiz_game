package commons;

import java.io.File;
import java.util.Objects;

public class PostActivity{

    Activity activity;
    private File picture;
    String writeTo;

    public PostActivity(Activity activity, String writeTo) {
        this.activity = activity;
        this.picture = new File(activity.getPicturePath());
        this.writeTo = writeTo;
    }

    public PostActivity() {}

    public File getPicture() {
        return picture;
    }

    public Activity getActivity() {
        return activity;
    }

    public String getWriteTo() {
        return writeTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostActivity)) return false;
        PostActivity that = (PostActivity) o;
        return Objects.equals(getActivity(), that.getActivity()) && Objects.equals(getPicture(),
                that.getPicture()) && Objects.equals(getWriteTo(), that.getWriteTo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getActivity(), getPicture());
    }
}
