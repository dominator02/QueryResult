package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Result extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("result.fxml"));
        primaryStage.setTitle("成绩结果");
        primaryStage.setScene(new Scene(root, 639, 447));
        primaryStage.show();
        primaryStage.setOnCloseRequest(windowEvent ->{
            try {
                SocketSingleIntance socketSingleIntance=SocketSingleIntance.getSingleIntance();
                socketSingleIntance.Close();
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } );
    }
    public static void main(String[] args) {
        launch(args);
    }




}
