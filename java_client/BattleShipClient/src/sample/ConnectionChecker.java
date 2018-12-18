package sample;

import controllers.SceneChanger;
import javafx.application.Platform;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class ConnectionChecker extends Thread{

    String hostName;
    boolean isRunning = false;
    boolean isAlertShown = false;

    /*
    public ConnectionChecker (){
        try {
            this.hostName = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    */

    public ConnectionChecker(String ipAddress){
        this.hostName = ipAddress;
    }


    private boolean isReachableByPing(String host) {
        try{
            String cmd = "";
            if(System.getProperty("os.name").startsWith("Windows")) {
                // For Windows
                cmd = "ping -n 1 " + host;
            } else {
                // For Linux and OSX
                cmd = "ping -c 1 " + host;
            }

            Process myProcess = Runtime.getRuntime().exec(cmd);
            myProcess.waitFor();

            if(myProcess.exitValue() == 0) {

                return true;
            } else {

                return false;
            }

        } catch( Exception e ) {

            e.printStackTrace();
            return false;
        }
    }

    private void setGuiDisable(int currentScene){

        switch (currentScene){
            case 1:     Receiver.getControllerFirstScene().gui.setDisable(true);
            break;

            case 2:     Receiver.getControllerSecondScene().gui.setDisable(true);
            break;

            case 3:     Receiver.getControllerThirdScene().gui.setDisable(true);
            break;

            default:    System.out.println("Error: disable invalid scene!");
        }

    }


    @Override
    public void run(){

        isRunning = true;


        while (isRunning){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean result = isReachableByPing(hostName);

            if(!result && !isAlertShown){
                Platform.runLater(() -> Validation.printAlert2("Lost connection to the internet."));
                System.out.println("Lost connection to the internet.");
                setGuiDisable(SceneChanger.currentScene);
                isAlertShown = true;
            }else if(result && isAlertShown){
                DataHelper.getSender().sendMessage("sync;");
                isAlertShown = false;
            }


        }

    }

    public synchronized void close(){
        this.isRunning = false;
    }

}
