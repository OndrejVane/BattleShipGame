package controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import sample.DataHelper;
import sample.Game;
import sample.Main;
import sample.Validation;

import java.io.IOException;


public class ControllerThirdScene {

    @FXML
    public BorderPane gui;

    @FXML
    private Button myBtn00;

    @FXML
    private Button myBtn01;

    @FXML
    private Button myBtn02;

    @FXML
    private Button myBtn03;

    @FXML
    private Button myBtn04;

    @FXML
    private Button myBtn05;

    @FXML
    private Button myBtn10;

    @FXML
    private Button myBtn11;

    @FXML
    private Button myBtn12;

    @FXML
    private Button myBtn13;

    @FXML
    private Button myBtn14;

    @FXML
    private Button myBtn15;

    @FXML
    private Button myBtn20;

    @FXML
    private Button myBtn21;

    @FXML
    private Button myBtn22;

    @FXML
    private Button myBtn23;

    @FXML
    private Button myBtn24;

    @FXML
    private Button myBtn25;

    @FXML
    private Button myBtn30;

    @FXML
    private Button myBtn31;

    @FXML
    private Button myBtn32;

    @FXML
    private Button myBtn33;

    @FXML
    private Button myBtn34;

    @FXML
    private Button myBtn35;

    @FXML
    private Button myBtn40;

    @FXML
    private Button myBtn41;

    @FXML
    private Button myBtn42;

    @FXML
    private Button myBtn43;

    @FXML
    private Button myBtn44;

    @FXML
    private Button myBtn45;

    @FXML
    private Button myBtn50;

    @FXML
    private Button myBtn51;

    @FXML
    private Button myBtn52;

    @FXML
    private Button myBtn53;

    @FXML
    private Button myBtn54;

    @FXML
    private Button myBtn55;

    @FXML
    public Button enemyBtn00;

    @FXML
    private Button enemyBtn01;

    @FXML
    private Button enemyBtn02;

    @FXML
    private Button enemyBtn03;

    @FXML
    private Button enemyBtn04;

    @FXML
    private Button enemyBtn05;

    @FXML
    private Button enemyBtn10;

    @FXML
    private Button enemyBtn11;

    @FXML
    private Button enemyBtn12;

    @FXML
    private Button enemyBtn13;

    @FXML
    private Button enemyBtn14;

    @FXML
    private Button enemyBtn15;

    @FXML
    private Button enemyBtn20;

    @FXML
    private Button enemyBtn21;

    @FXML
    private Button enemyBtn22;

    @FXML
    private Button enemyBtn23;

    @FXML
    private Button enemyBtn24;

    @FXML
    private Button enemyBtn25;

    @FXML
    private Button enemyBtn30;

    @FXML
    private Button enemyBtn31;

    @FXML
    private Button enemyBtn32;

    @FXML
    private Button enemyBtn33;

    @FXML
    private Button enemyBtn34;

    @FXML
    private Button enemyBtn35;

    @FXML
    private Button enemyBtn40;

    @FXML
    private Button enemyBtn41;

    @FXML
    private Button enemyBtn42;

    @FXML
    private Button enemyBtn43;

    @FXML
    private Button enemyBtn44;

    @FXML
    private Button enemyBtn45;

    @FXML
    private Button enemyBtn50;

    @FXML
    private Button enemyBtn51;

    @FXML
    private Button enemyBtn52;

    @FXML
    private Button enemyBtn53;

    @FXML
    private Button enemyBtn54;

    @FXML
    private Button enemyBtn55;

    @FXML
    private TextField numberOfMyShips;

    @FXML
    private TextField numberOfEnemyShips;

    @FXML
    private GridPane enemyGridPane;

    @FXML
    public Circle statusCircle;

    @FXML
    public TextField statusText;

    private Button[][] myGrid = new Button[6][6];

    private Button[][] enemyGrid = new Button[6][6];

    public Game game;


    private void initMyButtons() {
        myGrid[0][0] = myBtn00;
        myGrid[0][1] = myBtn01;
        myGrid[0][2] = myBtn02;
        myGrid[0][3] = myBtn03;
        myGrid[0][4] = myBtn04;
        myGrid[0][5] = myBtn05;
        myGrid[1][0] = myBtn10;
        myGrid[1][1] = myBtn11;
        myGrid[1][2] = myBtn12;
        myGrid[1][3] = myBtn13;
        myGrid[1][4] = myBtn14;
        myGrid[1][5] = myBtn15;
        myGrid[2][0] = myBtn20;
        myGrid[2][1] = myBtn21;
        myGrid[2][2] = myBtn22;
        myGrid[2][3] = myBtn23;
        myGrid[2][4] = myBtn24;
        myGrid[2][5] = myBtn25;
        myGrid[3][0] = myBtn30;
        myGrid[3][1] = myBtn31;
        myGrid[3][2] = myBtn32;
        myGrid[3][3] = myBtn33;
        myGrid[3][4] = myBtn34;
        myGrid[3][5] = myBtn35;
        myGrid[4][0] = myBtn40;
        myGrid[4][1] = myBtn41;
        myGrid[4][2] = myBtn42;
        myGrid[4][3] = myBtn43;
        myGrid[4][4] = myBtn44;
        myGrid[4][5] = myBtn45;
        myGrid[5][0] = myBtn50;
        myGrid[5][1] = myBtn51;
        myGrid[5][2] = myBtn52;
        myGrid[5][3] = myBtn53;
        myGrid[5][4] = myBtn54;
        myGrid[5][5] = myBtn55;
    }

    private void initEnemyButtons() {

        enemyGrid[0][0] = enemyBtn00;
        enemyGrid[0][1] = enemyBtn01;
        enemyGrid[0][2] = enemyBtn02;
        enemyGrid[0][3] = enemyBtn03;
        enemyGrid[0][4] = enemyBtn04;
        enemyGrid[0][5] = enemyBtn05;
        enemyGrid[1][0] = enemyBtn10;
        enemyGrid[1][1] = enemyBtn11;
        enemyGrid[1][2] = enemyBtn12;
        enemyGrid[1][3] = enemyBtn13;
        enemyGrid[1][4] = enemyBtn14;
        enemyGrid[1][5] = enemyBtn15;
        enemyGrid[2][0] = enemyBtn20;
        enemyGrid[2][1] = enemyBtn21;
        enemyGrid[2][2] = enemyBtn22;
        enemyGrid[2][3] = enemyBtn23;
        enemyGrid[2][4] = enemyBtn24;
        enemyGrid[2][5] = enemyBtn25;
        enemyGrid[3][0] = enemyBtn30;
        enemyGrid[3][1] = enemyBtn31;
        enemyGrid[3][2] = enemyBtn32;
        enemyGrid[3][3] = enemyBtn33;
        enemyGrid[3][4] = enemyBtn34;
        enemyGrid[3][5] = enemyBtn35;
        enemyGrid[4][0] = enemyBtn40;
        enemyGrid[4][1] = enemyBtn41;
        enemyGrid[4][2] = enemyBtn42;
        enemyGrid[4][3] = enemyBtn43;
        enemyGrid[4][4] = enemyBtn44;
        enemyGrid[4][5] = enemyBtn45;
        enemyGrid[5][0] = enemyBtn50;
        enemyGrid[5][1] = enemyBtn51;
        enemyGrid[5][2] = enemyBtn52;
        enemyGrid[5][3] = enemyBtn53;
        enemyGrid[5][4] = enemyBtn54;
        enemyGrid[5][5] = enemyBtn55;
    }


    @FXML
    public void initialize() {

        initMyButtons();
        initEnemyButtons();
        game = DataHelper.getGame();
        updateMyPane();
        updateEnemyPane(-1);
        DataHelper.getReciever().setControllerThirdScene(this);
        numberOfMyShips.setText(String.valueOf(DataHelper.game.playerLives));
        numberOfEnemyShips.setText(String.valueOf(DataHelper.game.enemyLives));
    }

    public void updateMyPane() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (game.myPane[i][j] == 1) {

                    myGrid[i][j].setStyle("-fx-background-image: url('/img/ship2.jpg'); -fx-background-repeat: no-repeat;");
                } else if (game.myPane[i][j] == 2) {
                    myGrid[i][j].setStyle("-fx-background-image: url('/img/fire.png'); -fx-background-repeat: no-repeat; -fx-background-position: 10px center;");
                } else if (game.myPane[i][j] == 3) {
                    myGrid[i][j].setStyle("-fx-background-image: url('/img/miss.png'); -fx-background-repeat: no-repeat;");
                } else {
                    myGrid[i][j].setStyle("-fx-background-image: url('/img/bg_button.jpg'); -fx-background-repeat: no-repeat; -fx-background-position: 10px center;");
                }
            }
        }
    }

    public void updateLives() {
        numberOfMyShips.setText(String.valueOf(DataHelper.game.playerLives));
        numberOfEnemyShips.setText(String.valueOf(DataHelper.game.enemyLives));
    }

    public void setEnemyGridPaneDisable() {
        updateLives();
        enemyGridPane.setDisable(true);
    }

    public void setEnemyGridPaneEnable() {
        updateLives();
        enemyGridPane.setDisable(false);
    }


    public void updateEnemyPane(int result) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (game.enemyPane[i][j] == -1) {
                    if (result == 1) {
                        enemyGrid[i][j].setStyle("-fx-background-image: url('/img/fire.png'); -fx-background-repeat: no-repeat; -fx-background-position: 10px center;");
                        game.enemyPane[i][j] = 2;
                    } else if (result == 0) {
                        enemyGrid[i][j].setStyle("-fx-background-image: url('/img/miss.png'); -fx-background-repeat: no-repeat;");
                        game.enemyPane[i][j] = 3;
                    }else if(result == -1){
                        //zatím nic nedělat!
                    }
                }
            }
        }
    }

    public void updateEnemyPane() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                    if (game.enemyPane[i][j] == 2) {
                        enemyGrid[i][j].setStyle("-fx-background-image: url('/img/fire.png'); -fx-background-repeat: no-repeat; -fx-background-position: 10px center;");
                    } else if (game.enemyPane[i][j] == 3) {
                        enemyGrid[i][j].setStyle("-fx-background-image: url('/img/miss.png'); -fx-background-repeat: no-repeat;");
                    }
            }
        }
    }

    public void changeToFirstScene() {
        Parent thirdScene = null;
        try {
            thirdScene = FXMLLoader.load(getClass().getResource("/scenes/first_scene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene secondViewScene = new Scene(thirdScene);
        Main.getStage().setScene(secondViewScene);
    }

    public void showAlert(String message) {
        Validation.printAlert(message);
    }

    @FXML
    private void fire00() {
        if (game.enemyPane[0][0] == 0) {
            DataHelper.getSender().sendMessage("fire;0;0;");
            game.enemyPane[0][0] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire01() {
        if (game.enemyPane[0][1] == 0) {
            DataHelper.getSender().sendMessage("fire;0;1;");
            game.enemyPane[0][1] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire02() {
        if (game.enemyPane[0][2] == 0) {
            DataHelper.getSender().sendMessage("fire;0;2;");
            game.enemyPane[0][2] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire03() {
        if (game.enemyPane[0][3] == 0) {
            DataHelper.getSender().sendMessage("fire;0;3;");
            game.enemyPane[0][3] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire04() {
        if (game.enemyPane[0][4] == 0) {
            DataHelper.getSender().sendMessage("fire;0;4;");
            game.enemyPane[0][4] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire05() {
        if (game.enemyPane[0][5] == 0) {
            DataHelper.getSender().sendMessage("fire;0;5;");
            game.enemyPane[0][5] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire10() {
        if (game.enemyPane[1][0] == 0) {
            DataHelper.getSender().sendMessage("fire;1;0;");
            game.enemyPane[1][0] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire11() {
        if (game.enemyPane[1][1] == 0) {
            DataHelper.getSender().sendMessage("fire;1;1;");
            game.enemyPane[1][1] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire12() {
        if (game.enemyPane[1][2] == 0) {
            DataHelper.getSender().sendMessage("fire;1;2;");
            game.enemyPane[1][2] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire13() {
        if (game.enemyPane[1][3] == 0) {
            DataHelper.getSender().sendMessage("fire;1;3;");
            game.enemyPane[1][3] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire14() {
        if (game.enemyPane[1][4] == 0) {
            DataHelper.getSender().sendMessage("fire;1;4;");
            game.enemyPane[1][4] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire15() {
        if (game.enemyPane[1][5] == 0) {
            DataHelper.getSender().sendMessage("fire;1;5;");
            game.enemyPane[1][5] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire20() {
        if (game.enemyPane[2][0] == 0) {
            DataHelper.getSender().sendMessage("fire;2;0;");
            game.enemyPane[2][0] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire21() {
        if (game.enemyPane[2][1] == 0) {
            DataHelper.getSender().sendMessage("fire;2;1;");
            game.enemyPane[2][1] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire22() {
        if (game.enemyPane[2][2] == 0) {
            DataHelper.getSender().sendMessage("fire;2;2;");
            game.enemyPane[2][2] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire23() {
        if (game.enemyPane[2][3] == 0) {
            DataHelper.getSender().sendMessage("fire;2;3;");
            game.enemyPane[2][3] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire24() {
        if (game.enemyPane[2][4] == 0) {
            DataHelper.getSender().sendMessage("fire;2;4;");
            game.enemyPane[2][4] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire25() {
        if (game.enemyPane[2][5] == 0) {
            DataHelper.getSender().sendMessage("fire;2;5;");
            game.enemyPane[2][5] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire30() {
        if (game.enemyPane[3][0] == 0) {
            DataHelper.getSender().sendMessage("fire;3;0;");
            game.enemyPane[3][0] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire31() {
        if (game.enemyPane[3][1] == 0) {
            DataHelper.getSender().sendMessage("fire;3;1;");
            game.enemyPane[3][1] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire32() {
        if (game.enemyPane[3][2] == 0) {
            DataHelper.getSender().sendMessage("fire;3;2;");
            game.enemyPane[3][2] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire33() {
        if (game.enemyPane[3][3] == 0) {
            DataHelper.getSender().sendMessage("fire;3;3;");
            game.enemyPane[3][3] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire34() {
        if (game.enemyPane[3][4] == 0) {
            DataHelper.getSender().sendMessage("fire;3;4;");
            game.enemyPane[3][4] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire35() {
        if (game.enemyPane[3][5] == 0) {
            DataHelper.getSender().sendMessage("fire;3;5;");
            game.enemyPane[3][5] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire40() {
        if (game.enemyPane[4][0] == 0) {
            DataHelper.getSender().sendMessage("fire;4;0;");
            game.enemyPane[4][0] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire41() {
        if (game.enemyPane[4][1] == 0) {
            DataHelper.getSender().sendMessage("fire;4;1;");
            game.enemyPane[4][1] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire42() {
        if (game.enemyPane[4][2] == 0) {
            DataHelper.getSender().sendMessage("fire;4;2;");
            game.enemyPane[4][2] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire43() {
        if (game.enemyPane[4][3] == 0) {
            DataHelper.getSender().sendMessage("fire;4;3;");
            game.enemyPane[4][3] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire44() {
        if (game.enemyPane[4][4] == 0) {
            DataHelper.getSender().sendMessage("fire;4;4;");
            game.enemyPane[4][4] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire45() {
        if (game.enemyPane[4][5] == 0) {
            DataHelper.getSender().sendMessage("fire;4;5;");
            game.enemyPane[4][5] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire50() {
        if (game.enemyPane[5][0] == 0) {
            DataHelper.getSender().sendMessage("fire;5;0;");
            game.enemyPane[5][0] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire51() {
        if (game.enemyPane[5][1] == 0) {
            DataHelper.getSender().sendMessage("fire;5;1;");
            game.enemyPane[5][1] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire52() {
        if (game.enemyPane[5][2] == 0) {
            DataHelper.getSender().sendMessage("fire;5;2;");
            game.enemyPane[5][2] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire53() {
        if (game.enemyPane[5][3] == 0) {
            DataHelper.getSender().sendMessage("fire;5;3;");
            game.enemyPane[5][3] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire54() {
        if (game.enemyPane[5][4] == 0) {
            DataHelper.getSender().sendMessage("fire;5;4;");
            game.enemyPane[5][4] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }

    @FXML
    private void fire55() {
        if (game.enemyPane[5][5] == 0) {
            DataHelper.getSender().sendMessage("fire;5;5;");
            game.enemyPane[5][5] = -1;
            setEnemyGridPaneDisable();
        } else {
            Validation.printAlert("You already shot on this place! Try again.");
        }
    }
}
