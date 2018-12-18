package sample;

import controllers.ControllerFirstScene;
import controllers.ControllerSecondScene;
import controllers.ControllerThirdScene;
import controllers.SceneChanger;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;


public class Receiver extends Thread{

    /**
     * Odkaz na socket zajistujici spojeni se serverem
     */
    private Socket socket;

    private BufferedReader bfr;

    private boolean isRunning = false;

    private static ControllerFirstScene controllerFirstScene;
    private static ControllerSecondScene controllerSecondScene;
    private static ControllerThirdScene controllerThirdScene;


    public Receiver(Socket s)
    {
        this.socket = s;
    }

    public void setControllerFirstScene(ControllerFirstScene controllerFirstScene){
        this.controllerFirstScene = controllerFirstScene;
    }

    public void setControllerSecondScene(ControllerSecondScene controllerSecondScene){
        this.controllerSecondScene = controllerSecondScene;
    }

    public void setControllerThirdScene(ControllerThirdScene controllerThirdScene){
        this.controllerThirdScene = controllerThirdScene;
    }

    public static ControllerFirstScene getControllerFirstScene() {
        return controllerFirstScene;
    }

    public static ControllerSecondScene getControllerSecondScene() {
        return controllerSecondScene;
    }

    public static ControllerThirdScene getControllerThirdScene() {
        return controllerThirdScene;
    }


    @Override
    public void run() {

        isRunning = true;

        try{
            char pole[] = new char[255];
            bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            String message;
            String command[];


            while(isRunning){
                if(bfr.read(pole) == -1){
                    String msg = "Lost connection to server!";
                    System.out.println(msg);
                    Platform.runLater(() -> Validation.printAlert("Lost connection to server!"));
                    Thread.sleep(2000);
                    DataHelper.connected = false;
                    socket.close();
                    isRunning = false;
                    Platform.runLater(() -> DataHelper.sceneChanger.changeToFirstScene());
                    break;

                }
                message = new String(pole);
                message = message.replaceAll("\0","");
                //message = message.replaceAll("#", "");
                command = message.split(";");


                //if(!message.equals("")){
                    System.out.println("From server: " +  message);
                //}


                if(command[0].equals("opponent")){
                    if (command[1].equals("found")){

                        Platform.runLater(() -> DataHelper.sceneChanger.changeToSecondScene());

                    }else if(command[1].equals("not_found")){
                        Platform.runLater(() -> Validation.printAlert("No player found!"));
                        Platform.runLater(() -> controllerFirstScene.findOpponentBtn.setDisable(false));
                        Platform.runLater(() -> controllerFirstScene.reconnectToGameBtn.setDisable(false));
                        Platform.runLater(() -> controllerFirstScene.setStatusText("connected"));
                        Platform.runLater(() -> controllerFirstScene.playerName.setEditable(true));
                    }else if(command[1].equals("full")){
                        Platform.runLater(() -> Validation.printAlert("Server is full"));
                        Platform.runLater(() -> controllerFirstScene.findOpponentBtn.setDisable(false));
                        Platform.runLater(() -> controllerFirstScene.reconnectToGameBtn.setDisable(false));
                        Platform.runLater(() -> controllerFirstScene.setStatusText("connected"));
                        Platform.runLater(() -> controllerFirstScene.playerName.setEditable(true));
                    }
                }else if(command[0].equals("start")){
                    Platform.runLater(() -> DataHelper.sceneChanger.changeToThirdScene());
                    if(command[1].equals("1")){
                        Platform.runLater(() -> controllerThirdScene.statusCircle.setFill(Color.GREEN));
                        Platform.runLater(() -> controllerThirdScene.statusText.setText("Your turn"));
                    }else if (command[1].equals("0")){
                        Platform.runLater(() -> controllerThirdScene.statusCircle.setFill(Color.RED));
                        Platform.runLater(() -> controllerThirdScene.statusText.setText("Enemy turn"));
                        Platform.runLater(() -> controllerThirdScene.setEnemyGridPaneDisable());
                    }
                }else if(command[0].equals("response")){
                    if(command[1].equals("1")){
                        Platform.runLater(() -> controllerThirdScene.updateEnemyPane(1));
                        Platform.runLater(() -> controllerThirdScene.statusCircle.setFill(Color.RED));
                        Platform.runLater(() -> controllerThirdScene.statusText.setText("Enemy turn"));
                        DataHelper.game.enemyLives--;
                    }else if(command[1].equals("0")){
                        Platform.runLater(() -> controllerThirdScene.updateEnemyPane(0));
                        Platform.runLater(() -> controllerThirdScene.statusCircle.setFill(Color.RED));
                        Platform.runLater(() -> controllerThirdScene.statusText.setText("Enemy turn"));
                    }else if (command[0].equals("-1")){
                        Platform.runLater(() -> controllerThirdScene.showAlert("You already shot on this place!"));
                    }

                }else if(command[0].equals("enemy")){
                    if (command[1].equals("1")){
                        if(Validation.checkArrayIndex(Integer.parseInt(command[2]),Integer.parseInt(command[3]))){
                            Platform.runLater(() -> controllerThirdScene.statusCircle.setFill(Color.GREEN));
                            Platform.runLater(() -> controllerThirdScene.statusText.setText("Your turn"));
                            Platform.runLater(() -> controllerThirdScene.setEnemyGridPaneEnable());
                            DataHelper.game.myPane[Integer.parseInt(command[2])][Integer.parseInt(command[3])] = 2;
                            Platform.runLater(() -> controllerThirdScene.updateMyPane());
                            DataHelper.game.playerLives--;
                        }else {
                            System.out.println("Wrong index of array!");
                            disconnectClient();
                        }
                    }else if (command[1].equals("0")){
                        if(Validation.checkArrayIndex(Integer.parseInt(command[2]),Integer.parseInt(command[3]))){
                            Platform.runLater(() -> controllerThirdScene.statusCircle.setFill(Color.GREEN));
                            Platform.runLater(() -> controllerThirdScene.statusText.setText("Your turn"));
                            Platform.runLater(() -> controllerThirdScene.setEnemyGridPaneEnable());
                            DataHelper.game.myPane[Integer.parseInt(command[2])][Integer.parseInt(command[3])] = 3;
                            Platform.runLater(() -> controllerThirdScene.updateMyPane());
                        }else {
                            System.out.println("Wrong index of array!");
                            disconnectClient();
                        }
                    }
                }else if (command[0].equals("result")){
                    if(command[1].equals("win")){
                        Platform.runLater(() -> controllerThirdScene.showAlert("YOU WIN!!"));
                        Thread.sleep(2000);
                        Platform.runLater(() -> DataHelper.sceneChanger.changeToFirstScene());
                    }else if(command[1].equals("lose")){
                        Platform.runLater(() -> controllerThirdScene.showAlert("YOU LOSE!!"));
                        Thread.sleep(2000);
                        Platform.runLater(() -> DataHelper.sceneChanger.changeToFirstScene());
                    }else if(command[1].equals("disconnect")){
                        Platform.runLater(() -> Validation.printAlert("Opponent has disconnected!!"));
                        Thread.sleep(2000);
                        Platform.runLater(() -> DataHelper.sceneChanger.changeToFirstScene());
                    }
                }else if (command[0].equals("reconnect")){
                    if(command[1].equals("success")){
                        Thread.sleep(2000);
                        DataHelper.getSender().sendMessage("sync;");
                    }else if(command[1].equals("fail")){
                        Platform.runLater(() -> Validation.printAlert("No game found!"));
                        Platform.runLater(() -> controllerFirstScene.findOpponentBtn.setDisable(false));
                        Platform.runLater(() -> controllerFirstScene.reconnectToGameBtn.setDisable(false));
                        Platform.runLater(() -> controllerFirstScene.setStatusText("connected"));
                        Platform.runLater(() -> controllerFirstScene.playerName.setEditable(true));
                    }
                }else if (command[0].equals("sync")){
                    setGuiEnable(SceneChanger.currentScene);
                    if(command[1].equals("set")){
                        if(Validation.checkSyncMessage(command)){
                            DataHelper.game = new Game();
                            Platform.runLater(() -> DataHelper.sceneChanger.changeToThirdScene());
                            if(command[2].equals("your_turn")){
                                Platform.runLater(() -> controllerThirdScene.statusCircle.setFill(Color.GREEN));
                                Platform.runLater(() -> controllerThirdScene.statusText.setText("Your turn"));
                                Platform.runLater(() -> controllerThirdScene.setEnemyGridPaneEnable());
                            }else if(command[2].equals("enemy_turn")){
                                Platform.runLater(() -> controllerThirdScene.statusCircle.setFill(Color.RED));
                                Platform.runLater(() -> controllerThirdScene.statusText.setText("Enemy turn"));
                                Platform.runLater(() -> controllerThirdScene.setEnemyGridPaneDisable());
                            }
                            DataHelper.game.initPanesAfterReconnect(command);
                            Platform.runLater(() -> controllerThirdScene.updateEnemyPane());
                            Platform.runLater(() -> controllerThirdScene.updateMyPane());
                            Platform.runLater(() -> controllerThirdScene.updateLives());
                        }else {
                            DataHelper.getSender().sendMessage("sync;");
                        }

                    }else if(command[1].equals("not_set")){
                        Platform.runLater(() -> DataHelper.sceneChanger.changeToSecondScene());
                    }
                }else if (command[0].equals("fail")){
                    if(command[1].equals("disconnected")){
                        System.out.println("Fail: Server disconnect this client!");
                        disconnectClient();
                    }else if (command[1].equals("sync")){
                        System.out.println("Fail: Synchronization is not permitted in this moment!");
                    }else {
                        System.out.println("Fail: " +  command[1]);
                    }

                }else if(command[0].equals("ping")){
                    DataHelper.sender.sendMessage("ping;");
                }else {
                    System.out.println("Wrong message from the server!");
                    disconnectClient();
                }

                for(int j=0; j<pole.length; j++){
                    pole[j] = 0;
                }
            }
        } catch(SocketTimeoutException e){
            System.out.println("Receiver: STX");
        }catch (SocketException f){
            System.out.println("Receiver: SX");
        }catch (InterruptedException g){
            System.out.println("Receiver: IE");
        }catch (IOException h){
            System.out.println("Receiver: IO");
        }
    }

    public synchronized void close() {
        isRunning = false;
    }

    public void disconnectClient(){
        try {
            System.out.println("The client was disconnected from the server.");
            Platform.runLater(() -> Validation.printAlert("Client was disconnected from the server."));
            Thread.sleep(2000);
            DataHelper.connected = false;
            socket.close();
            isRunning = false;
            Platform.runLater(() -> DataHelper.sceneChanger.changeToFirstScene());

        }catch (InterruptedException e){
            System.out.println("InterruptedException in disconnectingClient");
        }catch (IOException e){
            System.out.println("IOException in disconnectingClient");
        }

    }

    private void setGuiEnable(int currentScene) {

        switch (currentScene){
            case 1:     Receiver.getControllerFirstScene().gui.setDisable(false);
                break;

            case 2:     Receiver.getControllerSecondScene().gui.setDisable(false);
                break;

            case 3:     Receiver.getControllerThirdScene().gui.setDisable(false);
                break;

            default:    System.out.println("Error: enable invalid scene!");
        }
    }

}
