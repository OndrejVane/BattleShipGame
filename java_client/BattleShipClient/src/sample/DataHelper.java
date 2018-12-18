package sample;


import controllers.SceneChanger;

/**
 * DataHelper je třída, ve které se uchovávají reference na instance,
 * jsou potřeba v průběhu aplikace. Předávání dat mezi scenami.
 */
public class DataHelper {


    public static Game game = null;

    public static Sender sender = null;

    public static Receiver reciever = null;

    public static boolean connected = false;

    public static String playerName = null;

    public static String ipAdress = null;

    public static String port = null;

    public static SceneChanger sceneChanger = null;

    public static ConnectionChecker connectionChecker = null;




    public static void setGame(Game game){
        DataHelper.game = game;
    }

    public static Game getGame(){
        return DataHelper.game;
    }

    public static Sender getSender() {
        return sender;
    }

    public static void setSender(Sender sender) {
        DataHelper.sender = sender;
    }

    public static Receiver getReciever() {
        return reciever;
    }

    public static void setReciever(Receiver reciever) {
        DataHelper.reciever = reciever;
    }

}
