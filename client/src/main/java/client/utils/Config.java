package client.utils;

public class Config {
    public static ClassLoader loader = commons.Config.class.getClassLoader();
    public static String backgroundMusic = loader.getResource("music.mp3").toExternalForm();
    public static String buttonClickSound = loader.getResource("button.mp3").toExternalForm();
    public static String gearImage = loader.getResource("images/gear.png").toExternalForm();
    public static String deleteImage = loader.getResource("images/delete.png").toExternalForm();
    public static String nameFile = loader.getResource("playerName.txt").toExternalForm();
    public static String notificationStyle = "-fx-font-size: 20; -fx-text-fill: ";
    public static int activitiesPerPage = 5;

    public static String playerName;
    public static boolean isWaiting = false;
    /*public static String backgroundMusic = new File("music.mp3").toURI().toString();
    public static String buttonClickSound = new File("button.mp3").toURI().toString();*/
}
