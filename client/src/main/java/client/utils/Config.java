package client.utils;


public class Config {
    public static ClassLoader loader = commons.Config.class.getClassLoader();
    public static String backgroundMusic = loader.getResource("music.mp3").toExternalForm();
    public static String buttonClickSound = loader.getResource("button.mp3").toExternalForm();
    public static String gearImage = loader.getResource("images/gear.png").toExternalForm();
    public static String deleteImage = loader.getResource("images/delete.png").toExternalForm();
    public static String notificationStyle = "-fx-font-size: 20; -fx-text-fill: ";
    public static int activitiesPerPage = 5;
    public static Long allowedPing = 200L; /*ping in ms that is allowed for the client, if server takes longer than this
    then client will report a failed connection*/
    public static String playerName = "placeholder";
    /*public static String backgroundMusic = new File("music.mp3").toURI().toString();
    public static String buttonClickSound = new File("button.mp3").toURI().toString();*/
}
