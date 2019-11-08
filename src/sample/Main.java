package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("登录入口");
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
