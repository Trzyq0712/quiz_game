package commons;

import java.util.Objects;

public class Answer {
    private long answer;
    private long timeToAnswer;

    public Answer(long answer, long timeToAnswer) {
        this.answer = answer;
        this.timeToAnswer = timeToAnswer;
    }

    public long getAnswer() {
        return answer;
    }

    public long getTimeToAnswer() {
        return timeToAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer1 = (Answer) o;
        return answer == answer1.answer && timeToAnswer == answer1.timeToAnswer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(answer, timeToAnswer);
    }
}
