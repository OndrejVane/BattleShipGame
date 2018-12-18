package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import sample.DataHelper;
import sample.Game;
import sample.Sender;
import sample.Validation;

public class ControllerSecondScene {

    @FXML
    public BorderPane gui;

    @FXML
    private Button ReadyBtn;

    @FXML
    private Button btn11;

    @FXML
    private Button btn12;

    @FXML
    private Button btn42;

    @FXML
    private Button btn55;

    @FXML
    private Button btn22;

    @FXML
    private Button btn31;

    @FXML
    private Button btn32;

    @FXML
    private Button btn41;

    @FXML
    private Button btn21;

    @FXML
    private Button btn63;

    @FXML
    private Button btn35;

    @FXML
    private Button btn43;

    @FXML
    private Button btn51;

    @FXML
    private Button btn56;

    @FXML
    private Button btn62;

    @FXML
    private Button btn52;

    @FXML
    private Button btn53;

    @FXML
    private Button btn61;

    @FXML
    private Button btn64;

    @FXML
    private Button btn54;

    @FXML
    private Button btn44;

    @FXML
    private Button btn33;

    @FXML
    private Button btn34;

    @FXML
    private Button btn23;

    @FXML
    private Button btn16;

    @FXML
    private Button btn24;

    @FXML
    private Button btn36;

    @FXML
    private Button btn13;

    @FXML
    private Button btn26;

    @FXML
    private Button btn45;

    @FXML
    private Button btn14;

    @FXML
    private Button btn46;

    @FXML
    private Button btn15;

    @FXML
    private Button btn65;

    @FXML
    private Button btn25;

    @FXML
    private Button btn66;

    @FXML
    private TextField shipsToPlace;

    @FXML
    private TextField statusTextField;



    Game game = new Game();

    public void initialize(){
        DataHelper.getReciever().setControllerSecondScene(this);
        DataHelper.setGame(game);
    }

    public void setReadyBtnEnable(){
        if(shipsToPlace.getText().equals("0")){
            ReadyBtn.setDisable(false);
        }
    }


    public void sendGrid() {
        String messageGrid = "grid;";
        for (int i = 0; i<game.myPane.length; i++){
            for (int j = 0; j<game.myPane.length; j++){
                if(game.myPane[i][j] == 1){
                    messageGrid = messageGrid+i+";"+j+";";
                }
            }
        }
        Sender sender = DataHelper.getSender();
        ReadyBtn.setDisable(true);
        sender.sendMessage(messageGrid);
        System.out.println("Mesage grid: "+messageGrid);
        statusTextField.setText("Waiting for opponent!");




    }

    public void putShip11(){
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(1,1);
            if (result){
                btn11.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip12(){
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(1,2);
            if (result){
                btn12.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }

    }

    public void putShip42(){
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(4,2);
            if (result){
                btn42.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }

    }

    public void putShip22() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(2,2);
            if (result){
                btn22.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip31() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(3,1);
            if (result){
                btn31.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip32() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(3,2);
            if (result){
                btn32.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip41() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(4,1);
            if (result){
                btn41.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip21() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(2,1);
            if (result){
                btn21.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip43() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(4,3);
            if (result){
                btn43.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip51() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(5,1);
            if (result){
                btn51.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip52() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(5,2);
            if (result){
                btn52.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip53() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(5,3);
            if (result){
                btn53.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip54() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(5,4);
            if (result){
                btn54.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip44() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(4,4);
            if (result){
                btn44.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip33() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(3,3);
            if (result){
                btn33.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip34() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(3,4);
            if (result){
                btn34.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip23() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(2,3);
            if (result){
                btn23.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip24() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(2,4);
            if (result){
                btn24.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip13() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(1,3);
            if (result){
                btn13.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip14() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(1,4);
            if (result){
                btn14.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip61() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(6,1);
            if (result){
                btn61.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip62() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(6,2);
            if (result){
                btn62.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip63() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(6,3);
            if (result){
                btn63.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip64() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(6,4);
            if (result){
                btn64.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip15() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(1,5);
            if (result){
                btn15.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip35() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(3,5);
            if (result){
                btn35.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip25() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(2,5);
            if (result){
                btn25.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip45() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(4,5);
            if (result){
                btn45.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip55() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(5,5);
            if (result){
                btn55.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip16() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(1,6);
            if (result){
                btn16.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip65() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(6,5);
            if (result){
                btn65.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip26() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(2,6);
            if (result){
                btn26.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip36() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(3,6);
            if (result){
                btn36.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip46() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(4,6);
            if (result){
                btn46.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip56() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(5,6);
            if (result){
                btn56.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

    public void putShip66() {
        if(game.getShipsToPlace()>0){
            boolean result = game.putShip(6,6);
            if (result){
                btn66.setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                shipsToPlace.setText(Integer.toString(Integer.parseInt(shipsToPlace.getText())-1));
                setReadyBtnEnable();
            }
        }else {
            Validation.printAlert("You already place all ships!");
        }
    }

}
