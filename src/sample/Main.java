package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Stu.fxml"));
        primaryStage.setTitle("登录");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }




    public static void main(String[] args) throws IOException {

        launch(args);
    }


}
