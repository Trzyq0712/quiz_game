package commons;

import java.util.Objects;

public class Answer {
    private long answer;
    private long timeToAnswer;

    /**
     * @param answer - answer of question
     * @param timeToAnswer - time taken to submit the answer
     */
    public Answer(long answer, long timeToAnswer) {
        this.answer = answer;
        this.timeToAnswer = timeToAnswer;
    }

    /**
     * @return the answer
     */
    public long getAnswer() {
        return answer;
    }

    /**
     * @return the time taken to answer
     */
    public long getTimeToAnswer() {
        return timeToAnswer;
    }

    /**
     * @param o - other object we are comparing to
     * @return if o is equal to Answer
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer1 = (Answer) o;
        return answer == answer1.answer && timeToAnswer == answer1.timeToAnswer;
    }

    /**
     * @return hash of answer
     */
    @Override
    public int hashCode() {
        return Objects.hash(answer, timeToAnswer);
    }
}
