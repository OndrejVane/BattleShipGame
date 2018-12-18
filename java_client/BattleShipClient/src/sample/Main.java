package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application {

    private static Stage guiStage;

    public static Stage getStage(){
        return guiStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //inicializace úvodní scény
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/first_scene.fxml"));
        primaryStage.setTitle("Battle Ship");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
        guiStage = primaryStage;


        //přesměrování standardního výstupu do souboru
        File file = new File("client.log");
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        System.setOut(ps);


        //výpis zprávy o startu klienta do logu
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss yyyy/MM/dd");
        LocalDateTime start = LocalDateTime.now();
        System.out.println("Client was launched at "+dtf.format(start));

        //ukončení klienta po stisknutí křížku
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            LocalDateTime end = LocalDateTime.now();
            System.out.println("Client was terminated at "+dtf.format(end));
            if (DataHelper.connectionChecker != null){
                if (DataHelper.connectionChecker.isRunning){
                    DataHelper.connectionChecker.close();
                }
            }
            if (DataHelper.connected){
                DataHelper.getReciever().close();
            }
            System.exit(0);

        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
