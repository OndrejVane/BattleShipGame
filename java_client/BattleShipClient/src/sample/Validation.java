package sample;

import javafx.scene.control.Alert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Validation {


    public static boolean validIP(String ip) {
        if (ip == null || ip.isEmpty()) return false;
        ip = ip.trim();
        if ((ip.length() < 6) & (ip.length() > 15)) return false;

        try {
            Pattern pattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
            Matcher matcher = pattern.matcher(ip);
            return matcher.matches();
        } catch (PatternSyntaxException ex) {
            return false;
        }
    }


    public static boolean validPort(String inputPort){
        int port;
        try {
            port = Integer.parseInt(inputPort);

            if(port >= 1 && port <= 65535){
                return true;
            }
        }catch (NumberFormatException e){
            return false;
        }
        return false;
    }

    public static void printAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void printAlert2(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();

    }

    public static boolean checkArrayIndex(int x, int y){
        if (x>=0 && x<=5 && y>=0 && y<=5){
            return true;
        }else {
            return false;
        }
    }

    public static boolean checkSyncMessage(String[] message){

        if(message.length == 77){
            return true;
        }else {
            return false;
        }
    }
}
