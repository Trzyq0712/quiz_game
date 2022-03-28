package client.utils;


public class Config {
    public static ClassLoader loader = commons.Config.class.getClassLoader();
    public static String backgroundMusic = loader.getResource("music.mp3").toExternalForm();
    public static String buttonClickSound = loader.getResource("button.mp3").toExternalForm();
    /*public static String backgroundMusic = new File("music.mp3").toURI().toString();
    public static String buttonClickSound = new File("button.mp3").toURI().toString();*/
}
