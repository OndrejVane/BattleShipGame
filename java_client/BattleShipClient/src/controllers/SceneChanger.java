package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import sample.Main;

import java.io.IOException;

public class SceneChanger {

    public static int currentScene = 1;



    public void changeToFirstScene() {
        Parent thirdScene = null;
        try {
            thirdScene = FXMLLoader.load(getClass().getResource("/scenes/first_scene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene secondViewScene = new Scene(thirdScene);
        Main.getStage().setScene(secondViewScene);
        currentScene = 1;
    }

    public void changeToSecondScene() {
        Parent thirdScene = null;
        try {
            thirdScene = FXMLLoader.load(getClass().getResource("/scenes/second_scene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene secondViewScene = new Scene(thirdScene);
        Main.getStage().setScene(secondViewScene);
        currentScene = 2;
    }

    public void changeToThirdScene() {
        Parent thirdScene = null;
        try {
            thirdScene = FXMLLoader.load(getClass().getResource("/scenes/third_scene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene secondViewScene = new Scene(thirdScene);
        Main.getStage().setScene(secondViewScene);
        currentScene = 3;
    }
}
