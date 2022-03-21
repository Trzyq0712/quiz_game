package commons;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class PostActivity{

    Activity activity;
    private byte[] pictureBuffer;
    String writeTo;

    public PostActivity(Activity activity, byte[] pictureBuffer, String writeTo) {
        this.activity = activity;
        this.pictureBuffer = pictureBuffer;
        this.writeTo = writeTo;
    }

    public PostActivity() {}

    public byte[] getPictureBuffer() {
        return pictureBuffer;
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
        return Objects.equals(getActivity(), that.getActivity()) && Arrays.equals(pictureBuffer, that.pictureBuffer) && Objects.equals(getWriteTo(), that.getWriteTo());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getActivity(), getWriteTo());
        result = 31 * result + Arrays.hashCode(pictureBuffer);
        return result;
    }
}
