package commons;

import java.io.File;

public class Config {
    public static File backgroundMusic = new File("src/main/resources/music.mp3");
    public static File buttonClickSound = new File("src/main/resources/button2.mp3");
    public static String styleSheet = "style.css";
    public static String title = "The Energy Quiz";
    public static String quit = "Sure you want to quit?";
    public static String titleWaitingRoom = "The Waiting Room";
    public static double timePerQuestion = 5000; //milliseconds
    public static double timeForIntermediate = 5000; //milliseconds, time the intermediate leaderboard shows
    public static double timeAnswerReveal = 5000; //milliseconds, time the revealed answers show\
    public static int totalQuestions = 6; //total amount of questions in the quiz, set to 2 for testing purposes
    public static int timeReductionPercentage = 20; //percentage by which opponents time will decrease if the time
    //joker is played
    public static String edit = "Edit activities";
    public static int maxChatMessages = 6;//maximum amount of chatmessages that can be in the chat at once
    public static String serverImagePath = "server\\src\\main\\resources\\static\\activity\\newActivities\\";
    public static int maxCharsUsername = 20; //maximum amount of characters a name can contain
    public static String port = "7070"; //port for server and client
    public static String server = "http://localhost:" + port + "/";

}
