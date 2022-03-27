package client;

public class Config {
    public static ClassLoader loader = Config.class.getClassLoader();
    public static String backgroundMusic = loader.getResource("music.mp3").toExternalForm();
    public static String buttonClickSound = loader.getResource("button.mp3").toExternalForm();
    public static String styleSheet = "style.css";
    public static String title = "The Energy Quiz";
    public static String quit = "Sure you want to quit?";
    public static String titleWaitingRoom = "The Waiting Room";
    public static long timePerQuestion = 10000; //milliseconds
    public static long timeForIntermediate = 10000; //milliseconds, time the intermediate leaderboard shows
    public static long timeAnswerReveal = 5000; //milliseconds, time the revealed answers show\
    public static int totalQuestions = 6; //total amount of questions in the quiz, set to 2 for testing purposes
    public static int timeReductionPercentage = 20; //percentage by which opponents time will decrease if the time
    //joker is played
    public static String edit = "Edit activities";
    public static int maxChatMessages = 6;//maximum amount of chatmessages that can be in the chat at once
    public static String serverImagePath = "server\\src\\main\\resources\\static\\activity\\newActivities\\";
    public static int maxCharsUsername = 20; //maximum amount of characters a name can contain

}
