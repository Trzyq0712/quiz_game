package client;

import java.io.File;

public class Config {
    public static File backgroundMusic = new File("src/main/resources/music.mp3");
    public static File buttonClickSound = new File("src/main/resources/button2.mp3");
    public static String styleSheet = "style.css";
    public static String title = "The Energy Quiz";
    public static String quit = "Sure you want to quit?";
    public static String titleWaitingRoom = "The Waiting Room";
    public static double timePerQuestion = 3000; //milliseconds
    public static double timeForIntermediate = 2000; //milliseconds, time the intermediate leaderboard shows
    public static double timeAnswerReveal = 2000; //milliseconds, time the revealed answers show\
    public static int totalQuestions = 2; //total amount of questions in the quiz
    public static int timeReductionPercentage = 20; //percentage by which opponents time will decrease if the time
    //joker is played
}
