package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import sample.*;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ControllerFirstScene {

    @FXML
    public BorderPane gui;

    @FXML
    public TextField playerName;

    @FXML
    private TextField serverIPAddress;

    @FXML
    private TextField serverPort;

    @FXML
    private Button connectToServerBtn;

    @FXML
    public Button findOpponentBtn;

    @FXML
    public Button reconnectToGameBtn;

    @FXML
    private TextField statusTextField;


    private static String name;

    private Sender sender;




    public void connectToServer(javafx.event.ActionEvent event){
        name = playerName.getText();
        String ip = serverIPAddress.getText();
        String port = serverPort.getText();



        if(name.isEmpty()){
            Validation.printAlert("Name field is empty");
            return;
        }

        if (!Validation.validIP(ip)){
            Validation.printAlert("Input IP is not valid!");
            return;
        }

        if (!Validation.validPort(port)){
            Validation.printAlert("Input port is not valid!");
            return;
        }

        Socket socket;

        try {
            socket = new Socket(ip, Integer.parseInt(port));
        } catch (IOException e) {
            Validation.printAlert("No server available!");
            System.out.println("No server available!");
            return;
        }

        statusTextField.setText("connected");
        //inicializace senderu a recieveru
        sender = new Sender(socket);

        Receiver receiver = new Receiver(socket);
        receiver.setControllerFirstScene(this);
        receiver.start();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss yyyy/MM/dd");
        LocalDateTime start = LocalDateTime.now();
        System.out.println("Client was connected to server at "+dtf.format(start));
        System.out.println("Server address: "+ serverIPAddress.getText()+" port: "+ serverPort.getText());

        DataHelper.setSender(sender);
        DataHelper.setReciever(receiver);
        DataHelper.ipAdress = serverIPAddress.getText();
        DataHelper.port = serverPort.getText();
        DataHelper.connected = true;

        ConnectionChecker connectionChecker = new ConnectionChecker(DataHelper.ipAdress);
        DataHelper.connectionChecker = connectionChecker;
        DataHelper.connectionChecker.start();

        connectToServerBtn.setDisable(true);
        findOpponentBtn.setDisable(false);
        reconnectToGameBtn.setDisable(false);
        serverIPAddress.setEditable(false);
        serverPort.setEditable(false);

    }

    public void findOpponent(){
        name = playerName.getText();
        DataHelper.getReciever().setControllerFirstScene(this);


        if(name.isEmpty()){
            Validation.printAlert("Name field is empty");
            return;
        }

        statusTextField.setText("looking for an opponent");
        DataHelper.playerName = playerName.getText();

        findOpponentBtn.setDisable(true);
        reconnectToGameBtn.setDisable(true);
        playerName.setEditable(false);
        sender.sendMessage("find;"+name+";");

    }

    public void reconnectToGame(){
        name = playerName.getText();
        DataHelper.getReciever().setControllerFirstScene(this);

        if(name.isEmpty()){
            Validation.printAlert("Name field is empty");
            return;
        }

        DataHelper.playerName = playerName.getText();
        findOpponentBtn.setDisable(true);
        reconnectToGameBtn.setDisable(true);
        sender.sendMessage("reconnect;"+name+";");

    }

    public void setStatusText(String text)
    {
        statusTextField.setText(text);
        findOpponentBtn.setDisable(false);
        reconnectToGameBtn.setDisable(false);
    }

    public void initAfterGame(){
        statusTextField.setText("Connected");
        connectToServerBtn.setDisable(true);
        serverIPAddress.setEditable(false);
        serverPort.setEditable(false);
        findOpponentBtn.setDisable(false);
        reconnectToGameBtn.setDisable(false);
        serverPort.setText(DataHelper.port);
        playerName.setText(DataHelper.playerName);
        serverIPAddress.setText(DataHelper.ipAdress);
    }

    public void initialize(){
        SceneChanger sceneChanger = new SceneChanger();
        gui.setStyle("-fx-background-image: url('/img/bg.png');");
        //connectionChecker.start();
        DataHelper.sceneChanger = sceneChanger;
        if(DataHelper.connected){
            initAfterGame();
            DataHelper.game = null;
            sender = DataHelper.getSender();
        }
    }

}
